package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object ZTEPhones {
    val phones = listOf(
        Phone(id = "zte_axon_40", name = "ZTE Axon 40", brand = "ZTE", price = 44999,
            camera = CameraSpec("50MP f/1.6 + 50MP + 50MP", "20MP f/2.2", "8K@30fps"),
            processor = "Snapdragon 888", battery = BatterySpec(5000, "Li-Po"),
            display = DisplaySpec(6.7, "2400x1080", 120, "AMOLED"),
            charging = ChargingSpec(65, false, false), storage = StorageSpec(12, 256),
            features = listOf("5G", "AMOLED", "Fast Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.1, releaseYear = 2022)
    )
}
