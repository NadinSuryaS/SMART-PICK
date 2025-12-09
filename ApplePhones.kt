package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object ApplePhones {
    val phones = listOf(
        Phone(
            id = "apple_iphone_12",
            name = "iPhone 12",
            brand = "Apple",
            price = 59999,
            camera = CameraSpec(
                rear = "12MP f/1.6 + 12MP f/2.4",
                front = "12MP f/2.2",
                videoRecording = "4K@60fps"
            ),
            processor = "Apple A14 Bionic",
            battery = BatterySpec(2815, "Li-Ion"),
            display = DisplaySpec(6.1, "2532x1170", 60, "OLED"),
            charging = ChargingSpec(20, true, false),
            storage = StorageSpec(4, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "Water Resistant", "Stereo Speakers"),
            rating = 4.4,
            releaseYear = 2020
        ),
        Phone(
            id = "apple_iphone_13",
            name = "iPhone 13",
            brand = "Apple",
            price = 69999,
            camera = CameraSpec(
                rear = "12MP f/1.6 + 12MP f/2.4",
                front = "12MP f/2.2",
                videoRecording = "4K@60fps"
            ),
            processor = "Apple A15 Bionic",
            battery = BatterySpec(3240, "Li-Ion"),
            display = DisplaySpec(6.1, "2532x1170", 60, "OLED"),
            charging = ChargingSpec(20, true, false),
            storage = StorageSpec(4, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "Water Resistant", "Stereo Speakers"),
            rating = 4.5,
            releaseYear = 2021
        ),
        Phone(
            id = "apple_iphone_14",
            name = "iPhone 14",
            brand = "Apple",
            price = 79999,
            camera = CameraSpec(
                rear = "12MP f/1.5 + 12MP f/2.4",
                front = "12MP f/1.9",
                videoRecording = "4K@60fps"
            ),
            processor = "Apple A15 Bionic",
            battery = BatterySpec(3279, "Li-Ion"),
            display = DisplaySpec(6.1, "2532x1170", 60, "OLED"),
            charging = ChargingSpec(20, true, false),
            storage = StorageSpec(6, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "Water Resistant", "Stereo Speakers"),
            rating = 4.5,
            releaseYear = 2022
        ),
        Phone(
            id = "apple_iphone_14_pro",
            name = "iPhone 14 Pro",
            brand = "Apple",
            price = 99999,
            camera = CameraSpec(
                rear = "48MP f/1.78 + 12MP f/2.8 + 12MP f/1.78",
                front = "12MP f/1.9",
                videoRecording = "4K@60fps"
            ),
            processor = "Apple A16 Bionic",
            battery = BatterySpec(3200, "Li-Ion"),
            display = DisplaySpec(6.1, "2556x1179", 120, "OLED"),
            charging = ChargingSpec(27, true, false),
            storage = StorageSpec(6, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.6,
            releaseYear = 2022
        ),
        Phone(
            id = "apple_iphone_15_pro_max",
            name = "iPhone 15 Pro Max",
            brand = "Apple",
            price = 139999,
            camera = CameraSpec(
                rear = "48MP f/1.78 + 12MP f/2.8 + 12MP f/1.78",
                front = "12MP f/1.9",
                videoRecording = "4K@60fps"
            ),
            processor = "Apple A17 Pro",
            battery = BatterySpec(4323, "Li-Ion"),
            display = DisplaySpec(6.7, "2796x1290", 120, "OLED"),
            charging = ChargingSpec(35, true, false),
            storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers", "Large Storage"),
            rating = 4.7,
            releaseYear = 2023
        )
    )
}
