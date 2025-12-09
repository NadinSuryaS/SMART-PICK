package com.smartpick.mysp.data.phones

import com.smartpick.mysp.data.*

object HuaweiPhones {
    val phones = listOf(
        Phone(id = "huawei_p50", name = "Huawei P50", brand = "Huawei", price = 64999,
            camera = CameraSpec("50MP f/1.3 + 40MP + 12MP", "13MP f/2.4", "4K@60fps"),
            processor = "Snapdragon 888", battery = BatterySpec(4100, "Li-Po"),
            display = DisplaySpec(6.5, "2700x1224", 90, "OLED"),
            charging = ChargingSpec(66, true, false), storage = StorageSpec(8, 256),
            features = listOf("AMOLED", "Fast Charging", "Wireless Charging", "High Refresh Rate", "Water Resistant", "Stereo Speakers"),
            rating = 4.3, releaseYear = 2021)
    )
}
