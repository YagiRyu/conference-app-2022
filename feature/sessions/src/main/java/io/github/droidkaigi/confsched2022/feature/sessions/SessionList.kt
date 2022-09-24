package io.github.droidkaigi.confsched2022.feature.sessions

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import io.github.droidkaigi.confsched2022.model.TimetableItemId
import io.github.droidkaigi.confsched2022.model.TimetableItemWithFavorite

@Composable
fun SessionList(
    timetable: List<Pair<DurationTime, TimetableItemWithFavorite>>,
    sessionsListListState: LazyListState,
    onTimetableClick: (timetableItemId: TimetableItemId) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (Pair<DurationTime?, TimetableItemWithFavorite>) -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        val visibleItemsInfo = remember {
            derivedStateOf {
                sessionsListListState.layoutInfo.visibleItemsInfo
            }
        }
        var currentDurationTime: DurationTime? = null
        visibleItemsInfo.value.forEachIndexed { visibleItemIndex, visibleItemInfo ->
            val durationTime = timetable[visibleItemInfo.index].first
            if (currentDurationTime != durationTime) {
                currentDurationTime = durationTime
                val nextDurationTime = timetable.getOrNull(visibleItemInfo.index + 1)?.first
                val offsetDp = with(LocalDensity.current) {
                    visibleItemInfo.offset.toDp()
                }
                Box(
                    modifier = if (visibleItemIndex == 0 && durationTime == nextDurationTime) {
                        Modifier
                    } else {
                        Modifier.offset(x = 0.dp, y = offsetDp)
                    }
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(start = 12.dp, top = 12.dp)
                    ) {
                        Text(
                            text = durationTime.startAt,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Box(
                            modifier = Modifier
                                .size(1.dp, 2.dp)
                                .background(MaterialTheme.colorScheme.onBackground)
                        ) { }
                        Text(
                            text = durationTime.endAt,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = sessionsListListState,
    ) {
        itemsIndexed(timetable) { _, item ->
            key(item.second.timetableItem.id.value) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTimetableClick(item.second.timetableItem.id) }
                        .padding(12.dp)
                        .padding(start = 85.dp)
                ) {
                    content(item)
                }
            }
        }
    }
}
