package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object TecnoPhones {
    val phones = listOf(
        Phone(id = "tecno_spark_9", name = "Tecno Spark 9", brand = "Tecno", price = 8999,
            camera = CameraSpec("50MP f/1.6 + 0.3MP", "5MP f/2.0", "1080p@30fps"),
            processor = "MediaTek Helio G37", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.5, "1600x720", 60, "IPS"),
            charging = ChargingSpec(10, false, false), storage = StorageSpec(3, 32),
            features = listOf("Big Battery"), rating = 3.6, releaseYear = 2022),
        
        Phone(id = "tecno_camon_19", name = "Tecno Camon 19", brand = "Tecno", price = 14999,
            camera = CameraSpec("50MP f/1.6 + 2MP", "32MP f/2.0", "4K@30fps"),
            processor = "MediaTek Helio G88", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.8, "2460x1080", 90, "IPS"),
            charging = ChargingSpec(33, false, false), storage = StorageSpec(6, 128),
            features = listOf("5G", "Fast Charging", "High Refresh Rate"), rating = 3.9, releaseYear = 2023)
    )
}
