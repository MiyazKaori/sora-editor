/*******************************************************************************
 *    sora-editor - the awesome code editor for Android
 *    https://github.com/Rosemoe/sora-editor
 *    Copyright (C) 2020-2024  Rosemoe
 *
 *     This library is free software; you can redistribute it and/or
 *     modify it under the terms of the GNU Lesser General Public
 *     License as published by the Free Software Foundation; either
 *     version 2.1 of the License, or (at your option) any later version.
 *
 *     This library is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *     Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public
 *     License along with this library; if not, write to the Free Software
 *     Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 *     USA
 *
 *     Please contact Rosemoe by email 2073412493@qq.com if you need
 *     additional information or have any questions
 ******************************************************************************/

import com.android.build.api.dsl.LibraryExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin) apply false
	alias(libs.plugins.maven.publish)
}

import com.android.build.api.dsl.ApplicationExtension


fun Project.configureBaseExtension() {
    extensions.findByType(LibraryExtension::class.java)?.apply {
        compileSdk = 34
        println("Build with CI is ${System.getenv("CI")}")
        println("Build with JITPACK is ${System.getenv("JITPACK")}")
        
        if (System.getenv("CI") != null || System.getenv("JITPACK") != null ) {
            println("Auto select.")
        } else {
            buildToolsVersion = "34.0.4"
        }
        
        defaultConfig {
            minSdk = 26
        }
    }
}

fun Project.configureKotlinExtension() {
    extensions.findByType(KotlinAndroidProjectExtension::class.java)?.apply {
        jvmToolchain(17)
    }
}

subprojects {
    plugins.withId("com.android.library") {
        configureBaseExtension()
    }
    
    plugins.withId("org.jetbrains.kotlin.android") {
        configureKotlinExtension()
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.layout.buildDirectory)
}
