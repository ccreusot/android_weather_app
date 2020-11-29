package fr.cedriccreusot.data_adapter.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
    @Json(name = "city_info") val cityInfo: CityInfo?,
    @Json(name = "forecast_info") val forecastInfo: ForecastInfo?,
    @Json(name = "current_condition") val currentCondition: CurrentCondition?,
    @Json(name = "fcst_day_0") val fcstDay0: ForecastDay?,
    @Json(name = "fcst_day_1") val fcstDay1: ForecastDay?,
    @Json(name = "fcst_day_2") val fcstDay2: ForecastDay?,
    @Json(name = "fcst_day_3") val fcstDay3: ForecastDay?,
    @Json(name = "fcst_day_4") val fcstDay4: ForecastDay?
)

@JsonClass(generateAdapter = true)
data class CityInfo(
    @Json(name = "name") val name : String?,
    @Json(name = "country") val country : String?,
    @Json(name = "elevation") val elevation : String?,
    @Json(name = "sunrise") val sunrise : String?,
    @Json(name = "sunset") val sunset : String?
)

@JsonClass(generateAdapter = true)
data class ForecastInfo(
    @Json(name = "latitude") val latitude : String?,
    @Json(name = "longitude") val longitude : String?,
    @Json(name = "elevation") val elevation : String?
)

@JsonClass(generateAdapter = true)
data class CurrentCondition(
    @Json(name = "date") val date : String?,
    @Json(name = "hour") val hour : String?,
    @Json(name = "tmp") val tmp : Int?,
    @Json(name = "wnd_spd") val wndSpd : Int?,
    @Json(name = "wnd_gust") val wndGust : Int?,
    @Json(name = "wnd_dir") val wndDir : String?,
    @Json(name = "pressure") val pressure : Double?,
    @Json(name = "humidity") val humidity : Int?,
    @Json(name = "condition") val condition : String?,
    @Json(name = "condition_key") val conditionConditionKey : String?,
    @Json(name = "icon") val icon : String?,
    @Json(name = "icon_big") val iconBig : String?
)

@JsonClass(generateAdapter = true)
data class ForecastDay(
    @Json(name = "date") val date : String?,
    @Json(name = "day_short") val dayShort : String?,
    @Json(name = "day_long") val dayLong : String?,
    @Json(name = "tmin") val tmin : Int?,
    @Json(name = "tmax") val tmax : Int?,
    @Json(name = "condition") val condition : String?,
    @Json(name = "condition_key") val conditionConditionKey : String?,
    @Json(name = "icon") val icon : String?,
    @Json(name = "icon_big") val iconBig : String?,
    @Json(name = "hourly_data") val hourlyData : Map<String?, HourlyData>?
)

@JsonClass(generateAdapter = true)
data class HourlyData(
    @Json(name = "ICON") val iCON : String?,
    @Json(name = "CONDITION") val cONDITION : String?,
    @Json(name = "CONDITION_KEY") val cONDITION_KEY : String?,
    @Json(name = "TMP2m") val tMP2m : String?,
    @Json(name = "DPT2m") val dPT2m : String?,
    @Json(name = "WNDCHILL2m") val wNDCHILL2m : String?,
    @Json(name = "HUMIDEX") val hUMIDEX : String?,
    @Json(name = "RH2m") val rH2m : String?,
    @Json(name = "PRMSL") val pRMSL : String?,
    @Json(name = "APCPsfc") val aPCPsfc : String?,
    @Json(name = "WNDSPD10m") val wNDSPD10m : String?,
    @Json(name = "WNDGUST10m") val wNDGUST10m : String?,
    @Json(name = "WNDDIR10m") val wNDDIR10m : String?,
    @Json(name = "WNDDIRCARD10") val wNDDIRCARD10 : String?,
    @Json(name = "ISSNOW") val iSSNOW : String?,
    @Json(name = "HCDC") val hCDC : String?,
    @Json(name = "MCDC") val mCDC : String?,
    @Json(name = "LCDC") val lCDC : String?,
    @Json(name = "HGT0C") val hGT0C : String?,
    @Json(name = "KINDEX") val kINDEX : String?,
    @Json(name = "CAPE180_0") val cAPE1800 : String?,
    @Json(name = "CIN180_0") val cIN1800 : String?
)