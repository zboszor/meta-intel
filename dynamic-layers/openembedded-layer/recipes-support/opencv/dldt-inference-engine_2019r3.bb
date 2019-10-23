SUMMARY = "OpenVINO(TM) Toolkit - Deep Learning Deployment Toolkit"
HOMEPAGE = "https://github.com/opencv/dldt"
DESCRIPTION = "This toolkit allows developers to deploy pre-trained \
deep learning models through a high-level C++ Inference Engine API \
integrated with application logic."

SRC_URI = "git://github.com/opencv/dldt.git;protocol=git;branch=2019 \
           file://0001-R2-Build-fixes.patch;patchdir=../ \
           file://0002-R2-Install-DLDT-headers-libs-sample-Apps.patch;patchdir=../ \
           file://0003-use-GNUInstallDirs-on-nix.patch;patchdir=../ \
           file://0004-disable-werror.patch;patchdir=../ \
           file://0005-point-to-correct-location-of-ngraph-headers.patch;patchdir=../ \
           file://0006-Install-clDNN-plugin-to-CMAKE_INSTALL_LIBDIR.patch;patchdir=../ \
           file://0007-Install-mock_engine-to-CMAKE_INSTALL_LIBDIR.patch;patchdir=../ \
           file://run-ptest \
           "
SRCREV = "1c794d971cdd3c94de5ea4a6c9588eac63f4cc57"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://../LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

inherit cmake ptest python3native

S = "${WORKDIR}/git/inference-engine"

EXTRA_OECMAKE += " \
                  -DENABLE_VPU=OFF \
                  -DENABLE_OPENCV=0 \
                  -DENABLE_SAMPLES_CORE=1 \
                  -DENABLE_PLUGIN_RPATH=0 \
                  -DENABLE_GNA=0 \
                  -DPYTHON_EXECUTABLE=${PYTHON} \
                  -DTHREADING=TBB \
                  -DCMAKE_BUILD_TYPE=DebugWithRelInfo \
                  -DNGRAPH_INCLUDES=${STAGING_INCDIR}/ngraph \
                  -DENABLE_TESTS="${@bb.utils.contains('PTEST_ENABLED', '1', '1', '0', d)}" \
                  -DBUILD_GMOCK=1 \
                  -DBUILD_GTEST=0 \
                  -DINSTALL_GMOCK=0 \
                  -DINSTALL_GTEST=0 \
                  "

DEPENDS += "libusb1 \
            ade \
            opencv \
            pugixml \
            ngraph \
            tbb \
            ${@bb.utils.contains('PTEST_ENABLED', '1', 'gflags', '0', d)} \
            "

COMPATIBLE_HOST = '(x86_64).*-linux'
COMPATIBLE_HOST_libc-musl = "null"

PACKAGECONFIG ?= ""
PACKAGECONFIG[opencl] = "-DENABLE_CLDNN=1 -DCLDNN__IOCL_ICD_INCDIRS=${STAGING_INCDIR} -DCLDNN__IOCL_ICD_STLDIRS=${STAGING_LIBDIR} -DCLDNN__IOCL_ICD_SHLDIRS=${STAGING_LIBDIR}, -DENABLE_CLDNN=0, opencl-icd-loader, opencl-icd-loader intel-compute-runtime"
PACKAGECONFIG[python3] = "-DENABLE_PYTHON=ON -DPYTHON_LIBRARY=${PYTHON_LIBRARY} -DPYTHON_INCLUDE_DIR=${PYTHON_INCLUDE_DIR}, -DENABLE_PYTHON=OFF, python3-cython-native, python3"

do_install_ptest_base_prepend() {
        # While not a Makefile based project that strictly falls into the category of
        # what ptest helps with, adding the unit tests here as ptest would help.
        # Create a dummy Makefile so installation doesn't fail.
        touch ${WORKDIR}/Makefile
        mv ${D}${bindir}/InferenceEngineUnitTests ${D}${PTEST_PATH}/
	mv ${D}${libdir}/libmock_engine.so ${D}${PTEST_PATH}/
}

# Otherwise e.g. ros-openvino-toolkit-dynamic-vino-sample when using dldt-inference-engine uses dldt-inference-engine WORKDIR
# instead of RSS
SSTATE_SCAN_FILES_append = " *.cmake"

FILES_${PN}-dev = "${includedir} \
                   ${libdir}/cmake \
                   ${libdir}/libinference_engine.so \
                   "

FILES_${PN} += "${libdir}/lib*${SOLIBSDEV} \
                ${datadir}/openvino \
                "

# Move inference engine samples into a separate package
PACKAGES =+ "${PN}-samples"

FILES_${PN}-samples = "${bindir}"

# Package for inference engine python API
PACKAGES =+ "${PN}-${PYTHON_PN}"

FILES_${PN}-${PYTHON_PN} = "${PYTHON_SITEPACKAGES_DIR}/openvino"
