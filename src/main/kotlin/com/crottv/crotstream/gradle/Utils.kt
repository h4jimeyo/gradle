package com.crottv.crotstream.gradle

import org.gradle.api.Project
import com.crottv.crotstream.gradle.getCrotstream
import com.crottv.crotstream.gradle.entities.*
import groovy.json.JsonBuilder

fun Project.makeManifest(): PluginManifest {
    val extension = this.extensions.getCrotstream()

    require(extension.pluginClassName != null) {
        "No plugin class found, make sure your plugin class is annotated with @CrotstreamPlugin"
    }

    val version = this.version.toString().toIntOrNull(10)
    if (version == null) {
        logger.warn("'${project.version}' is not a valid version. Use an integer.")
    }

    return PluginManifest(
        pluginClassName = extension.pluginClassName,
        name = this.name,
        version = version ?: -1,
        requiresResources = extension.requiresResources
    )
}

fun Project.makePluginEntry(): PluginEntry {
    val extension = this.extensions.getCrotstream()

    val version = this.version.toString().toIntOrNull(10)
    if (version == null) {
        logger.warn("'${project.version}' is not a valid version. Use an integer.")
    }

    val repo = extension.repository

    return PluginEntry(
        url = (if (repo == null) "" else repo.getRawLink("${this.name}.crot", extension.buildBranch)),
        status = extension.status,
        version = version ?: -1,
        name = this.name,
        internalName = this.name,
        authors = extension.authors,
        description = extension.description,
        repositoryUrl = (if (repo == null) null else repo.url),
        language = extension.language,
        iconUrl = extension.iconUrl,
        apiVersion = extension.apiVersion,
        tvTypes = extension.tvTypes,
        fileSize = extension.fileSize
    )
}