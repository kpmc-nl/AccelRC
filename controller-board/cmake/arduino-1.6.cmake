file(STRINGS "arduino-path.txt" ARDUINO_ROOT)

if (NOT ARDUINO_ROOT)
    message(FATAL_ERROR "Specify the Arduino SDK path in the textfile arduino-path.txt")
endif ()


set(ARDUINO_LIB_DIR "${ARDUINO_ROOT}/libraries/")

enable_language(ASM)

set(ARDUINO_CORE_DIR "${ARDUINO_ROOT}/hardware/arduino/avr/cores/arduino/")
set(ARDUINO_PINS_DIR "${ARDUINO_ROOT}/hardware/arduino/avr/variants/${ARDUINO_VARIANT}")
set(AVRDUDE_CONFIG "${ARDUINO_ROOT}/hardware/tools/avr/etc/avrdude.conf")

set(AVR_GCC ${ARDUINO_ROOT}/hardware/tools/avr/bin/avr-gcc)
set(AVR_G++ ${ARDUINO_ROOT}/hardware/tools/avr/bin/avr-g++)
set(AVR_OBJCOPY ${ARDUINO_ROOT}/hardware/tools/avr/bin/avr-objcopy)
set(AVRDUDE ${ARDUINO_ROOT}/hardware/tools/avr/bin/avrdude -C${AVRDUDE_CONFIG})


include_directories(${ARDUINO_PINS_DIR})
include_directories(${ARDUINO_CORE_DIR})

set(ARDUINO_SOURCE_FILES
        ${ARDUINO_CORE_DIR}/wiring_pulse.S

        ${ARDUINO_CORE_DIR}/WInterrupts.c
        ${ARDUINO_CORE_DIR}/hooks.c
        ${ARDUINO_CORE_DIR}/wiring.c
        ${ARDUINO_CORE_DIR}/wiring_analog.c
        ${ARDUINO_CORE_DIR}/wiring_digital.c
        ${ARDUINO_CORE_DIR}/wiring_pulse.c
        ${ARDUINO_CORE_DIR}/wiring_shift.c

        ${ARDUINO_CORE_DIR}/CDC.cpp
        ${ARDUINO_CORE_DIR}/HardwareSerial.cpp
        ${ARDUINO_CORE_DIR}/HardwareSerial0.cpp
        ${ARDUINO_CORE_DIR}/HardwareSerial1.cpp
        ${ARDUINO_CORE_DIR}/HardwareSerial2.cpp
        ${ARDUINO_CORE_DIR}/HardwareSerial3.cpp
        ${ARDUINO_CORE_DIR}/IPAddress.cpp
        ${ARDUINO_CORE_DIR}/PluggableUSB.cpp
        ${ARDUINO_CORE_DIR}/Print.cpp
        ${ARDUINO_CORE_DIR}/Stream.cpp
        ${ARDUINO_CORE_DIR}/Tone.cpp
        ${ARDUINO_CORE_DIR}/USBCore.cpp
        ${ARDUINO_CORE_DIR}/WMath.cpp
        ${ARDUINO_CORE_DIR}/WString.cpp
        ${ARDUINO_CORE_DIR}/abi.cpp
        ${ARDUINO_CORE_DIR}/new.cpp
        )