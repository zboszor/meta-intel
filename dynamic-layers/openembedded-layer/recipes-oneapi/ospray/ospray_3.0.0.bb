SUMMARY  = "Intel OSPray, Ray Tracing based Rendering Engine"
DESCRIPTION = "Intel OSPRay is an open source, scalable, and portable ray \
tracing engine for high-performance, high-fidelity visualization on \
Intel Architecture CPUs."
HOMEPAGE = "https://www.ospray.org/"

LICENSE  = "Apache-2.0 & BSD-3-Clause & MIT & Zlib"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=1dece7821bf3fd70fe1309eaa37d52a2 \
                    file://third-party-programs.txt;md5=e37b77e3bd997abccc359c710fb1f1db \
                    "

inherit pkgconfig cmake

S = "${WORKDIR}/git"

SRC_URI = "git://github.com/ospray/ospray.git;protocol=https;branch=master \
            "
SRCREV = "66fa8108485a8a92ff31ad2e06081bbaf391bc26"

COMPATIBLE_HOST = '(x86_64).*-linux'
COMPATIBLE_HOST:libc-musl = "null"

DEPENDS = "rkcommon ispc ispc-native openvkl embree"

EXTRA_OECMAKE += " \
                  -DISPC_EXECUTABLE=${STAGING_BINDIR_NATIVE}/ispc \
                  -DOSPRAY_ENABLE_APPS_BENCHMARK=OFF \
                  -DOSPRAY_ENABLE_APPS_TESTING=OFF \
                  "

PACKAGES =+ "${PN}-apps"
FILES:${PN}-apps = "\
                     ${bindir} \
                     "

UPSTREAM_CHECK_GITTAGREGEX = "^v(?P<pver>(\d+(\.\d+)+))$"
