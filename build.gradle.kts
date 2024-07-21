buildscript {
    val compose_variable by extra("1.6.4")
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("androidx.room") version "2.6.0" apply false
    id("com.google.dagger.hilt.android") version "2.50" apply false
}