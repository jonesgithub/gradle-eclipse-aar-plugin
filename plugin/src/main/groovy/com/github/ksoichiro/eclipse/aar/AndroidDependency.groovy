package com.github.ksoichiro.eclipse.aar

class AndroidDependency {
    static final String SEPARATOR = '-'

    String group
    String name
    String version
    File file
    AndroidArtifactType artifactType

    String getQualifiedName() {
        if (isProject()) {
            return name
        }
        if (isRawJar()) {
            return filenameExtStripped()
        }
        if (!group && !name && !version) {
            return filenameExtStripped()
        }
        def list = []
        if (group && !group.isEmpty()) {
            list << group
        }
        if (name && !name.isEmpty()) {
            list << name
        }
        if (version && !version.isEmpty()) {
            list << version
        }
        list.join(SEPARATOR)
    }

    boolean isProject() {
        artifactType == AndroidArtifactType.PROJECT
    }

    boolean isRawJar() {
        artifactType == AndroidArtifactType.RAW_JAR
    }

    boolean isSameArtifact(AndroidDependency dependency) {
        dependency && artifactType == dependency.artifactType && group == dependency.group && name == dependency.name
    }

    boolean isSameArtifactVersion(AndroidDependency dependency) {
        isSameArtifact(dependency) && version == dependency.version
    }

    String filenameExtStripped() {
        file?.name?.lastIndexOf('.')?.with {
            it != -1 ? file.name[0..<it] : file.name
        } ?: ""
    }
}
