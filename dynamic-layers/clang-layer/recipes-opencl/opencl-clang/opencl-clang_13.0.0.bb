SUMMARY = "Common clang is a thin wrapper library around clang"
DESCRIPTION = "Common clang has OpenCL-oriented API and is capable \
 to compile OpenCL C kernels to SPIR-V modules."

LICENSE = "NCSA"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e8a15bf1416762a09ece07e44c79118c"

SRC_URI = "git://github.com/intel/opencl-clang.git;branch=ocl-open-130;protocol=https \
           file://273.patch \
           "
SRCREV = "e0d718e677c1c65236aa1a8b5f8e4a3f3cc24fcb"

S = "${WORKDIR}/git"

inherit cmake
DEPENDS += " clang spirv-llvm-translator"
DEPENDS:append:class-target = " clang-native opencl-clang-native"

COMPATIBLE_HOST = '(x86_64).*-linux'
COMPATIBLE_HOST:libc-musl = "null"

EXTRA_OECMAKE += "\
                  -DCMAKE_SKIP_RPATH=TRUE \
                  -DPREFERRED_LLVM_VERSION=${LLVMVERSION} \
                  "

do_install:append:class-native() {
        install -d ${D}${bindir}
        install -m 0755 ${B}/linux_linker/linux_resource_linker ${D}${bindir}/
}

BBCLASSEXTEND = "native nativesdk"
