package com.vinibarros.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Model(
    @PrimaryKey(autoGenerate = true) val id: Long = 0
): Serializable