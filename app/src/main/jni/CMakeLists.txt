project(caytrongsdk)

cmake_minimum_required(VERSION 3.10)

set(OpenCV_DIR ${CMAKE_SOURCE_DIR}/opencv-mobile-4.6.0-android/sdk/native/jni)
find_package(OpenCV REQUIRED core imgproc highgui)

set(ncnn_DIR ${CMAKE_SOURCE_DIR}/ncnn-20230517-android-vulkan/${ANDROID_ABI}/lib/cmake/ncnn)
find_package(ncnn REQUIRED)

add_library(caytrongsdk SHARED caytrongsdk.cpp ndkcamera.cpp bo_detection.cpp ca_phe_detection.cpp dau_detection.cpp ngo_detection.cpp sau_rieng_detection.cpp)

target_link_libraries(caytrongsdk ncnn ${OpenCV_LIBS} camera2ndk mediandk)


