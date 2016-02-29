
if (LINUX)
    message("Found linux -- will download Linux nanobp distribution.")
    set(NANOPB_DIST nanopb-0.3.5-linux-x86)
elseif (APPLE)
    message("Found Apple -- will download MacOS nanopb distribution.")
    set(NANOPB_DIST nanopb-0.3.5-macosx-x86)
elseif (WIN32)
    message(FATAL_ERROR "The author of this code didn't have time to test on Windows. Please figure it out yourself")
else ()
    message(FATAL_ERROR "Unrecognized OS")
endif ()


if (NOT EXISTS "${CMAKE_BINARY_DIR}/${NANOPB_DIST}.tar.gz")
    file(DOWNLOAD http://koti.kapsi.fi/~jpa/nanopb/download/${NANOPB_DIST}.tar.gz ${CMAKE_BINARY_DIR}/${NANOPB_DIST}.tar.gz)
endif ()

execute_process(
        COMMAND ${CMAKE_COMMAND} -E tar xzf ${CMAKE_BINARY_DIR}/${NANOPB_DIST}.tar.gz
        WORKING_DIRECTORY ${CMAKE_BINARY_DIR}
)


set(NANOPB_DIR ${CMAKE_BINARY_DIR}/${NANOPB_DIST})


set(NANOPB_SRC

        #        ${NANOPB_DIR}/pb.h
        #
        #        ${NANOPB_DIR}/pb_common.h
        #        ${NANOPB_DIR}/pb_encode.h
        #        ${NANOPB_DIR}/pb_decode.h

        ${NANOPB_DIR}/pb_common.c
        ${NANOPB_DIR}/pb_encode.c
        ${NANOPB_DIR}/pb_decode.c
        )

include_directories(${NANOPB_DIR})
