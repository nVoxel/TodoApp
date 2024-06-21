package com.voxeldev.todoapp.utils.exceptions

/**
 * @author nvoxel
 */
class ItemNotFoundException(id: String) : Throwable(message = "Item with ID \"$id\" does not exist")
