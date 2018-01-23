package ca.mcgill.science.tepid

import org.gradle.api.Plugin
import org.gradle.api.Project

class TepidPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.extensions.create("tepid", Versions)
        project.extensions.create("tepidDependency", Dependencies)
    }

}