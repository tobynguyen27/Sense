package dev.tobynguyen27.sense.sync.annotation

import dev.tobynguyen27.sense.sync.blockentity.AutoPersistBlockEntity
import net.minecraft.world.level.block.entity.BlockEntity

/**
 * Marks a field in an [AutoPersistBlockEntity] subclass for automatic persistence.
 *
 * Fields annotated with [Persisted] will be automatically saved to and loaded from NBT data when
 * the level is saved or loaded.
 *
 * **Important:** After updating the value of a persisted field, call [BlockEntity.setChanged] to
 * mark the block entity as dirty and ensure changes are persisted during the next save.
 *
 * @param key The NBT key to use for persistence. If empty (default), the field's name is used as
 *   the key.
 */
@Target(AnnotationTarget.FIELD) annotation class Persisted(val key: String = "")
