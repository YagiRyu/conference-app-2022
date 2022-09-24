package io.github.droidkaigi.confsched2022.model

import kotlinx.collections.immutable.PersistentList
import kotlinx.coroutines.flow.Flow

public interface AnnouncementsRepository {
    public fun announcements(): Flow<PersistentList<AnnouncementsByDate>>
    public suspend fun refresh()
}
