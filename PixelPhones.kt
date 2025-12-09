package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object PixelPhones {
    val phones = listOf(
        Phone(id = "pixel_6a", name = "Pixel 6a", brand = "Google Pixel", price = 43999,
            camera = CameraSpec("12.2MP f/1.7", "8MP f/2.0", "4K@60fps"),
            processor = "Google Tensor", battery = BatterySpec(4410, "Li-Ion"),
            display = DisplaySpec(6.1, "2400x1080", 60, "OLED"),
            charging = ChargingSpec(18, false, false), storage = StorageSpec(6, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "Water Resistant"), rating = 4.2, releaseYear = 2021),
        
        Phone(id = "pixel_7", name = "Pixel 7", brand = "Google Pixel", price = 59999,
            camera = CameraSpec("50MP f/1.85 + 12MP f/1.7", "10.7MP f/2.2", "4K@60fps"),
            processor = "Google Tensor", battery = BatterySpec(4355, "Li-Ion"),
            display = DisplaySpec(6.3, "2400x1080", 90, "OLED"),
            charging = ChargingSpec(30, true, false), storage = StorageSpec(8, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant"),
            rating = 4.4, releaseYear = 2022),
        
        Phone(id = "pixel_7_pro", name = "Pixel 7 Pro", brand = "Google Pixel", price = 79999,
            camera = CameraSpec("50MP f/1.85 + 48MP + 12MP", "10.7MP f/2.2", "4K@60fps"),
            processor = "Google Tensor", battery = BatterySpec(5003, "Li-Ion"),
            display = DisplaySpec(6.7, "3120x1440", 120, "OLED"),
            charging = ChargingSpec(30, true, false), storage = StorageSpec(12, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.6, releaseYear = 2022),
        
        Phone(id = "pixel_8_pro", name = "Pixel 8 Pro", brand = "Google Pixel", price = 106999,
            camera = CameraSpec("50MP f/1.68 + 48MP + 48MP", "10.5MP f/2.2", "8K@30fps"),
            processor = "Google Tensor G3", battery = BatterySpec(5050, "Li-Ion"),
            display = DisplaySpec(6.7, "2992x1344", 120, "OLED"),
            charging = ChargingSpec(37, true, false), storage = StorageSpec(12, 512),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers", "Large Storage"),
            rating = 4.7, releaseYear = 2023)
    )
}
