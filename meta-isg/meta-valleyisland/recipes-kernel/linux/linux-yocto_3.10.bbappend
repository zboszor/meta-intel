FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

#############################
# MACHINE = valleyisland-32 #
#############################
COMPATIBLE_MACHINE_valleyisland-32 = "valleyisland-32"
KMACHINE_valleyisland-32 = "valleyisland-32"
KBRANCH_valleyisland-32 = "standard/base"
KERNEL_FEATURES_valleyisland-32 = " features/valleyisland-io/valleyisland-io.scc \
				    features/valleyisland-io/valleyisland-io-pci.scc"

LINUX_VERSION_valleyisland-32 = "3.10.65"
SRCREV_machine_valleyisland-32 = "ef1b96279e1a087a9d5b6a0601830d42ea47355c"
SRCREV_meta_valleyisland-32 = "d5456dd830cad14bd844753b751b83744ced3793"
SRCREV_valleyisland-io_valleyisland-32 = "0992d01f5f382f6da60004ef87f67ebd3ca13732"

SRC_URI_valleyisland-32 = "git://git.yoctoproject.org/linux-yocto-3.10.git;protocol=git;nocheckout=1;branch=${KBRANCH},${KMETA},valleyisland-io-3.0;name=machine,meta,valleyisland-io"

#############################
# MACHINE = valleyisland-64 #
#############################
COMPATIBLE_MACHINE_valleyisland-64 = "valleyisland-64"
KMACHINE_valleyisland-64 = "valleyisland"
KBRANCH_valleyisland-64 = "standard/base"
KERNEL_FEATURES_valleyisland-64 = " features/valleyisland-io/valleyisland-io.scc \
				    features/valleyisland-io/valleyisland-io-pci.scc"

LINUX_VERSION_valleyisland-64 = "3.10.65"
SRCREV_machine_valleyisland-64 = "ef1b96279e1a087a9d5b6a0601830d42ea47355c"
SRCREV_meta_valleyisland-64 = "d5456dd830cad14bd844753b751b83744ced3793"
SRCREV_valleyisland-io_valleyisland-64 = "0992d01f5f382f6da60004ef87f67ebd3ca13732"

SRC_URI_valleyisland-64 = "git://git.yoctoproject.org/linux-yocto-3.10.git;protocol=git;nocheckout=1;branch=${KBRANCH},${KMETA},valleyisland-io-3.0;name=machine,meta,valleyisland-io"

module_autoload_i2c-dev = "i2c-dev"
