cmake_minimum_required(VERSION 3.4.1)


file(GLOB my_source_path *.cpp *.h include/*.cpp include/*.h)
add_library(cpp_speech_features SHARED ${my_source_path})

find_library(log-lib
        log)

target_link_libraries( # Specifies the target library.
        cpp_speech_features
        ${log-lib})
