package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object SamsungPhones {
    val phones = listOf(
        // Budget Range
        Phone(
            id = "samsung_a04",
            name = "Samsung Galaxy A04",
            brand = "Samsung",
            price = 8999,
            camera = CameraSpec(
                rear = "50MP f/1.8",
                front = "5MP f/2.2",
                videoRecording = "1080p@30fps"
            ),
            processor = "MediaTek Helio G35",
            battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.5, "1600x720", 60, "IPS"),
            charging = ChargingSpec(10, false, false),
            storage = StorageSpec(3, 32),
            features = listOf("5G", "Big Battery", "Water Resistant"),
            rating = 3.8,
            releaseYear = 2023
        ),
        Phone(
            id = "samsung_m13",
            name = "Samsung Galaxy M13",
            brand = "Samsung",
            price = 9999,
            camera = CameraSpec(
                rear = "50MP f/1.8",
                front = "8MP f/2.0",
                videoRecording = "1080p@30fps"
            ),
            processor = "MediaTek Helio G99",
            battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.6, "1920x1080", 90, "IPS"),
            charging = ChargingSpec(25, false, false),
            storage = StorageSpec(4, 64),
            features = listOf("5G", "Big Battery", "Fast Charging", "High Refresh Rate"),
            rating = 4.0,
            releaseYear = 2023
        ),
        
        // Mid-Range
        Phone(
            id = "samsung_a54",
            name = "Samsung Galaxy A54",
            brand = "Samsung",
            price = 35999,
            camera = CameraSpec(
                rear = "50MP f/1.8 + 12MP + 5MP",
                front = "32MP f/2.2",
                videoRecording = "4K@60fps"
            ),
            processor = "Exynos 1280",
            battery = BatterySpec(5000, "Li-Ion"),
            display = DisplaySpec(6.4, "2340x1080", 120, "AMOLED"),
            charging = ChargingSpec(25, false, false),
            storage = StorageSpec(6, 128),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant"),
            rating = 4.2,
            releaseYear = 2023
        ),
        Phone(
            id = "samsung_a73",
            name = "Samsung Galaxy A73",
            brand = "Samsung",
            price = 49999,
            camera = CameraSpec(
                rear = "108MP f/1.8 + 12MP + 5MP",
                front = "32MP f/2.2",
                videoRecording = "8K@24fps"
            ),
            processor = "MediaTek Dimensity 7050",
            battery = BatterySpec(5000, "Li-Ion"),
            display = DisplaySpec(6.7, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(33, false, false),
            storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.3,
            releaseYear = 2023
        ),
        
        // Premium Range
        Phone(
            id = "samsung_s23",
            name = "Samsung Galaxy S23",
            brand = "Samsung",
            price = 74999,
            camera = CameraSpec(
                rear = "50MP f/1.8 + 10MP + 10MP",
                front = "12MP f/2.2",
                videoRecording = "8K@60fps"
            ),
            processor = "Snapdragon 8 Gen 2",
            battery = BatterySpec(4000, "Li-Ion"),
            display = DisplaySpec(6.1, "2340x1080", 120, "AMOLED"),
            charging = ChargingSpec(25, true, false),
            storage = StorageSpec(8, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.5,
            releaseYear = 2023
        ),
        Phone(
            id = "samsung_s23_ultra",
            name = "Samsung Galaxy S23 Ultra",
            brand = "Samsung",
            price = 124999,
            camera = CameraSpec(
                rear = "200MP f/1.8 + 50MP + 10MP + 10MP",
                front = "12MP f/2.2",
                videoRecording = "8K@60fps"
            ),
            processor = "Snapdragon 8 Gen 2",
            battery = BatterySpec(5000, "Li-Ion"),
            display = DisplaySpec(6.8, "3088x1440", 120, "AMOLED"),
            charging = ChargingSpec(45, true, false),
            storage = StorageSpec(12, 512),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers", "Stylus Support", "Large Storage"),
            rating = 4.7,
            releaseYear = 2023
        ),
        
        // Ultra Premium
        Phone(
            id = "samsung_z_fold5",
            name = "Samsung Galaxy Z Fold5",
            brand = "Samsung",
            price = 154999,
            camera = CameraSpec(
                rear = "50MP f/1.8 + 12MP + 10MP",
                front = "10MP f/2.2",
                videoRecording = "8K@60fps"
            ),
            processor = "Snapdragon 8 Gen 2",
            battery = BatterySpec(4400, "Li-Ion"),
            display = DisplaySpec(7.6, "2176x1812", 120, "AMOLED"),
            charging = ChargingSpec(25, true, false),
            storage = StorageSpec(12, 512),
            features = listOf("5G", "AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Foldable", "Stylus Support"),
            rating = 4.6,
            releaseYear = 2023
        )
    )
}
