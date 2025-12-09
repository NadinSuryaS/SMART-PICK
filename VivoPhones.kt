package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object VivoPhones {
    val phones = listOf(
        Phone(id = "vivo_y15", name = "Vivo Y15", brand = "Vivo", price = 10999,
            camera = CameraSpec("13MP f/2.2 + 2MP", "5MP f/2.2", "1080p@30fps"),
            processor = "MediaTek Helio G37", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.51, "1600x720", 60, "IPS"),
            charging = ChargingSpec(10, false, false), storage = StorageSpec(3, 32),
            features = listOf("Big Battery"), rating = 3.7, releaseYear = 2021),
        
        Phone(id = "vivo_v23", name = "Vivo V23", brand = "Vivo", price = 32999,
            camera = CameraSpec("50MP f/1.88 + 8MP + 2MP", "50MP f/2.0", "4K@60fps"),
            processor = "MediaTek Dimensity 920", battery = BatterySpec(4200, "Li-Po"),
            display = DisplaySpec(6.44, "2400x1080", 90, "AMOLED"),
            charging = ChargingSpec(44, false, false), storage = StorageSpec(8, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate"), rating = 4.1, releaseYear = 2022),
        
        Phone(id = "vivo_x80", name = "Vivo X80", brand = "Vivo", price = 59999,
            camera = CameraSpec("50MP f/1.75 + 12MP + 12MP", "32MP f/2.45", "8K@30fps"),
            processor = "MediaTek Dimensity 9000", battery = BatterySpec(4500, "Li-Po"),
            display = DisplaySpec(6.78, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(80, true, false), storage = StorageSpec(12, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.4, releaseYear = 2022),
        
        Phone(id = "vivo_x80_pro", name = "Vivo X80 Pro", brand = "Vivo", price = 79999,
            camera = CameraSpec("50MP f/1.75 + 50MP + 12MP + 8MP", "32MP f/2.45", "8K@30fps"),
            processor = "Snapdragon 8 Gen 1", battery = BatterySpec(4700, "Li-Po"),
            display = DisplaySpec(6.78, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(80, true, true), storage = StorageSpec(12, 512),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers", "Large Storage"),
            rating = 4.6, releaseYear = 2022)
    )
}
