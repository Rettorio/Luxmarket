package id.rettorio.luxmarket.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.rettorio.luxmarket.ui.theme.primaryLight
import id.rettorio.luxmarket.ui.theme.tertiaryContainerLight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun <T>LazyCarousel(
    items: List<T>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
    scope: CoroutineScope = rememberCoroutineScope(),
    itemContent: @Composable LazyItemScope.(T) -> Unit,
) {
    var itemIndex by remember { mutableIntStateOf(0) }
    var itemWidth by remember { mutableStateOf(150.dp) }
    val itemPadding: Dp = 8.dp
    val totalItemWidth = with(LocalDensity.current) { (itemWidth+itemPadding).toPx() }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyRow(
            state = listState,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(itemPadding)
        ) {
            items(items) { item ->
                itemContent(item).apply { Modifier.onGloballyPositioned { coordinates ->
                    itemWidth = coordinates.size.width.dp
                } }
            }
        }
        Spacer(Modifier.height(12.dp))
        CarouselStateBar(currentItem = itemIndex, itemSize = items.size - 1, onClick = { i: Int -> scope.launch { listState.animateScrollToItem(i) } })
        Spacer(Modifier.height(12.dp))
    }

    LaunchedEffect(Unit) {
        snapshotFlow { listState.isScrollInProgress }
            .collect{ isScrolling ->
                if(!isScrolling) {
                    itemIndex = calculateTargetIndex(
                        listState.firstVisibleItemIndex,
                        listState.firstVisibleItemScrollOffset,
                        totalItemWidth,
                        items.size
                    )
                    scope.launch {
                        listState.animateScrollToItem(index = itemIndex)
                    }
                }
            }
    }
}


@Composable
internal fun CarouselStateBar(
    currentItem: Int,
    itemSize: Int,
    onClick: (Int) -> Unit
) {
    val isEven = {num: Int -> num.mod(2) == 0}
    val stateBarWidth = 80
    val stateBarSpace = 5
    val stateBarSpaceTotal = if(isEven(itemSize)) itemSize * stateBarSpace else (itemSize - 1) * stateBarSpace
    val normalBarItemWidth = (stateBarWidth - stateBarSpaceTotal) / (itemSize + 2)

    Row(
        modifier = Modifier
            .width(stateBarWidth.dp)
            .height(4.dp),
        horizontalArrangement = Arrangement.spacedBy(stateBarSpace.dp)
    ) {
        for(i in 0.rangeTo(itemSize)) {
            val _barItemModifier: Modifier = if(i == currentItem) Modifier.weight(1f) else Modifier.width(normalBarItemWidth.dp)
            Box(
                modifier = _barItemModifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(32.dp))
                    .background(if (i == currentItem) primaryLight else tertiaryContainerLight)
                    .clickable {
                        if (i != currentItem) {
                            onClick(i)
                        }
                    }
            )
        }
    }
}


// Key Point 3: Calculating the Target Index for Snapping
internal fun calculateTargetIndex(
    firstVisibleItemIndex: Int,
    firstVisibleItemScrollOffset: Int,
    itemWidthPx: Float,
    itemCount: Int // Pass the total number of items in the list
): Int {
    // Calculate the total scroll offset in pixels
    val totalScrollOffset = firstVisibleItemIndex * itemWidthPx + firstVisibleItemScrollOffset
    // Calculate the index based on the scroll offset
    var targetIndex = (totalScrollOffset / itemWidthPx).toInt()

    // Determine the fraction of the item that is visible
    val visibleItemFraction = totalScrollOffset % itemWidthPx
    // If more than half of the item is shown, snap to the next item
    if (visibleItemFraction > itemWidthPx / 2) {
        targetIndex++
    }

    // Special case: when the user has scrolled to the end, snap to the last item
    if (targetIndex >= itemCount - 1) {
        targetIndex = itemCount - 1
    }

    return targetIndex
}