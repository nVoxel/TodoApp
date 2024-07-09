import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author nvoxel
 */

internal val Project.libs: LibrariesForLibs
    get() = (this as ExtensionAware).extensions.getByName("libs") as LibrariesForLibs
