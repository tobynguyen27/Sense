package dev.tobynguyen27.sense.sync.annotation

import dev.tobynguyen27.sense.sync.blockentity.AutoPersistBlockEntity
import dev.tobynguyen27.sense.sync.blockentity.AutoSyncBlockEntity
import net.minecraft.world.level.block.entity.BlockEntity

/**
 * Marks a field in an [AutoSyncBlockEntity] subclass for automatic synchronization.
 *
 * Fields annotated with [Synced] will be automatically synchronized from the server to the client.
 * This simplifies network synchronization for block entities by handling data packets without
 * requiring manual overrides or custom networking code.
 *
 * **Important:** After updating the value of a synced field, you do not need to call
 * [BlockEntity.setChanged] for synchronization purposes. However, if you also want to persist this
 * field to world data, combine this annotation with [Persisted] in an [AutoPersistBlockEntity]
 * subclass and call [BlockEntity.setChanged] to ensure changes are saved.
 *
 * @see AutoPersistBlockEntity
 */
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY) annotation class Synced()
