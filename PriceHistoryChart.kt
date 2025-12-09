package com.smartpick.mysp.ui.components

import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.smartpick.mysp.data.PricePoint
import com.smartpick.mysp.ui.theme.DarkBackground
import com.smartpick.mysp.ui.theme.PrimaryBlue
import com.smartpick.mysp.ui.theme.SurfaceDark
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

@Composable
fun PriceHistoryChart(
    priceHistory: List<PricePoint>,
    currentPrice: Int,
    lowestPrice: Int = 0,
    savings: Int = 0,
    timePeriod: Int = 7, // 7, 30, or 90 days
    modifier: Modifier = Modifier,
    onSetPriceAlert: () -> Unit = {}
) {
    // Filter price history based on time period
    val now = System.currentTimeMillis()
    val periodMillis = timePeriod * 24 * 60 * 60 * 1000L
    val filteredHistory = priceHistory.filter { abs(now - it.timestamp) <= periodMillis }
        .sortedBy { it.timestamp }
    
    // Calculate stats if not provided
    val lowestPriceValue = if (lowestPrice > 0) lowestPrice else (filteredHistory.minOfOrNull { it.price } ?: currentPrice)
    val highestPriceValue = filteredHistory.maxOfOrNull { it.price } ?: currentPrice
    val savingsValue = if (savings > 0) savings else (currentPrice - lowestPriceValue)
    
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                androidx.compose.ui.graphics.Color.White.copy(alpha = 0.95f),
                RoundedCornerShape(16.dp)
            )
            .padding(20.dp)
    ) {
        // Title with icon
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "📈",
                fontSize = 24.sp
            )
            Text(
                text = "Price History",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryBlue
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Chart
        if (filteredHistory.isNotEmpty()) {
            AndroidView(
                factory = { context ->
                    LineChart(context).apply {
                        // Setup chart
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        isHighlightPerTapEnabled = true
                        setBackgroundColor(Color.TRANSPARENT)
                        setGridBackgroundColor(Color.TRANSPARENT)
                        setNoDataText("No data available")
                        setPinchZoom(true)

                        // X-axis setup - DISABLED
                        xAxis.apply {
                            isEnabled = false
                        }

                        // Y-axis setup (left)
                        axisLeft.apply {
                            textColor = Color.parseColor("#999999")
                            textSize = 9f
                            setDrawGridLines(true)
                            gridColor = Color.parseColor("#E8E8E8")
                            gridLineWidth = 0.5f
                            valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    val thousands = value.toInt() / 1000
                                    return "₹${thousands}k"
                                }
                            }
                        }

                        // Y-axis setup (right)
                        axisRight.isEnabled = false

                        // Legend
                        legend.isEnabled = false

                        // Create data with filtered history
                        val entries = filteredHistory.mapIndexed { index, pricePoint ->
                            Entry(index.toFloat(), pricePoint.price.toFloat())
                        }

                        val dataSet = LineDataSet(entries, "Price").apply {
                            color = Color.parseColor("#0A84FF")
                            lineWidth = 2.5f
                            circleRadius = 5f
                            setDrawCircleHole(true)
                            circleHoleRadius = 2.5f
                            setDrawValues(false)
                            mode = LineDataSet.Mode.CUBIC_BEZIER
                            cubicIntensity = 0.2f
                            setDrawFilled(true)
                            fillColor = Color.parseColor("#0A84FF")
                            fillAlpha = 50
                            
                            // Colorful data points like in the model
                            val colors = listOf(
                                Color.parseColor("#FF6B6B"), // Red
                                Color.parseColor("#4ECDC4"), // Teal
                                Color.parseColor("#45B7D1"), // Blue
                                Color.parseColor("#FFA07A"), // Light Salmon
                                Color.parseColor("#98D8C8"), // Mint
                                Color.parseColor("#F7DC6F"), // Yellow
                                Color.parseColor("#BB8FCE"), // Purple
                                Color.parseColor("#85C1E2")  // Light Blue
                            )
                            
                            setCircleColors(colors.take(entries.size))
                        }

                        data = LineData(dataSet)
                        invalidate()
                        animateX(800)
                    }
                },
                update = { chart ->
                    // Update chart when data changes
                    val entries = filteredHistory.mapIndexed { index, pricePoint ->
                        Entry(index.toFloat(), pricePoint.price.toFloat())
                    }

                    val dataSet = LineDataSet(entries, "Price").apply {
                        color = PrimaryBlue.toArgb()
                        setCircleColor(PrimaryBlue.toArgb())
                        lineWidth = 2.5f
                        circleRadius = 5f
                        setDrawCircleHole(false)
                        setDrawValues(false)
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                        cubicIntensity = 0.2f
                        setDrawFilled(true)
                        fillColor = PrimaryBlue.toArgb()
                        fillAlpha = 30
                    }

                    chart.data = LineData(dataSet)
                    chart.invalidate()
                    chart.animateX(800)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        } else {
            // Empty state
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        androidx.compose.ui.graphics.Color.White.copy(alpha = 0.05f),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No price history available for this period",
                    fontSize = 14.sp,
                    color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.5f)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Price Stats Cards
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Current Price Card
            PriceStatCard(
                icon = "💰",
                label = "Current Price",
                price = "₹$currentPrice",
                backgroundColor = androidx.compose.ui.graphics.Color(0xFFE3F2FD),
                textColor = PrimaryBlue
            )

            // Lowest Price Card
            PriceStatCard(
                icon = "📊",
                label = "Lowest Price",
                price = "₹$lowestPriceValue",
                backgroundColor = androidx.compose.ui.graphics.Color(0xFFE8F5E9),
                textColor = androidx.compose.ui.graphics.Color(0xFF4CAF50)
            )

            // Highest Price Card
            PriceStatCard(
                icon = "📈",
                label = "Highest Price",
                price = "₹$highestPriceValue",
                backgroundColor = androidx.compose.ui.graphics.Color(0xFFFFEBEE),
                textColor = androidx.compose.ui.graphics.Color(0xFFFF6B6B)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Set Price Alert Button
        androidx.compose.material3.Button(
            onClick = onSetPriceAlert,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = androidx.compose.ui.graphics.Color(0xFFFF5722)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "🔔 Set Price Alert",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = androidx.compose.ui.graphics.Color.White
            )
        }
    }
}

@Composable
private fun PriceStatCard(
    icon: String,
    label: String,
    price: String,
    backgroundColor: androidx.compose.ui.graphics.Color,
    textColor: androidx.compose.ui.graphics.Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = icon,
                fontSize = 24.sp
            )
            Column {
                Text(
                    text = label,
                    fontSize = 12.sp,
                    color = androidx.compose.ui.graphics.Color.Gray,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Text(
            text = price,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
fun PriceVsTimeChart(
    priceHistory: List<PricePoint>,
    modifier: Modifier = Modifier
) {
    // Calculate weeks from price history
    val now = System.currentTimeMillis()
    val dataWithWeeks = priceHistory.map { pricePoint ->
        val weeksDiff = (now - pricePoint.timestamp) / (7 * 24 * 60 * 60 * 1000L)
        Pair(pricePoint.price.toFloat(), weeksDiff.toFloat())
    }.sortedBy { it.first } // Sort by price

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceDark, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "Price vs Time Analysis",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = androidx.compose.ui.graphics.Color.White
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Stats
        if (dataWithWeeks.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        androidx.compose.ui.graphics.Color.White.copy(alpha = 0.05f),
                        RoundedCornerShape(8.dp)
                    )
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Min Price
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Min Price",
                        fontSize = 11.sp,
                        color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "₹${dataWithWeeks.minOf { it.first }.toInt()}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color(0xFF4CAF50)
                    )
                }

                // Max Price
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Max Price",
                        fontSize = 11.sp,
                        color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "₹${dataWithWeeks.maxOf { it.first }.toInt()}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.ui.graphics.Color(0xFFFF9800)
                    )
                }

                // Time Span
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Time Span",
                        fontSize = 11.sp,
                        color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${dataWithWeeks.maxOf { it.second }.toInt()} weeks",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryBlue
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Chart
            AndroidView(
                factory = { context ->
                    LineChart(context).apply {
                        description.isEnabled = false
                        setTouchEnabled(true)
                        isDragEnabled = true
                        setScaleEnabled(true)
                        isHighlightPerTapEnabled = true
                        setBackgroundColor(Color.TRANSPARENT)
                        setGridBackgroundColor(Color.TRANSPARENT)
                        setNoDataText("No data available")

                        // X-axis setup (Price in Rupees)
                        xAxis.apply {
                            position = XAxis.XAxisPosition.BOTTOM
                            setDrawGridLines(false)
                            textColor = Color.WHITE
                            textSize = 10f
                            granularity = 1f
                            isGranularityEnabled = true
                            setAvoidFirstLastClipping(true)
                            valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return "₹${value.toInt()}"
                                }
                            }
                        }

                        // Y-axis setup (Time in Weeks)
                        axisLeft.apply {
                            textColor = Color.WHITE
                            textSize = 10f
                            setDrawGridLines(true)
                            gridColor = Color.WHITE.and(0x1FFFFFFF)
                            valueFormatter = object : ValueFormatter() {
                                override fun getFormattedValue(value: Float): String {
                                    return "${value.toInt()}w"
                                }
                            }
                        }

                        axisRight.isEnabled = false
                        legend.isEnabled = false

                        // Create data
                        val entries = dataWithWeeks.mapIndexed { index, (price, weeks) ->
                            Entry(price, weeks)
                        }

                        val dataSet = LineDataSet(entries, "Price vs Time").apply {
                            color = PrimaryBlue.toArgb()
                            setCircleColor(PrimaryBlue.toArgb())
                            lineWidth = 2.5f
                            circleRadius = 5f
                            setDrawCircleHole(false)
                            setDrawValues(false)
                            mode = LineDataSet.Mode.CUBIC_BEZIER
                            cubicIntensity = 0.2f
                            setDrawFilled(true)
                            fillColor = PrimaryBlue.toArgb()
                            fillAlpha = 30
                        }

                        data = LineData(dataSet)
                        invalidate()
                        animateX(800)
                    }
                },
                update = { chart ->
                    val entries = dataWithWeeks.mapIndexed { index, (price, weeks) ->
                        Entry(price, weeks)
                    }

                    val dataSet = LineDataSet(entries, "Price vs Time").apply {
                        color = PrimaryBlue.toArgb()
                        setCircleColor(PrimaryBlue.toArgb())
                        lineWidth = 2.5f
                        circleRadius = 5f
                        setDrawCircleHole(false)
                        setDrawValues(false)
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                        cubicIntensity = 0.2f
                        setDrawFilled(true)
                        fillColor = PrimaryBlue.toArgb()
                        fillAlpha = 30
                    }

                    chart.data = LineData(dataSet)
                    chart.invalidate()
                    chart.animateX(800)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(
                        androidx.compose.ui.graphics.Color.White.copy(alpha = 0.05f),
                        RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No price history available",
                    fontSize = 14.sp,
                    color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.5f)
                )
            }
        }
    }
}
