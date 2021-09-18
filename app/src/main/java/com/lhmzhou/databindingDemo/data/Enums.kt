package com.lhmzhou.databindingDemo.data

//This enum class is for facilitating the switch between UI state
enum class UIState {
    //While user wait for data to be fetched, a loading indicator is shown
    LOADING,

    //When there is data to show, list can become visible
    HAS_DATA,

    //When there is no data in the inventory, empty image will be shown
    EMPTY
}

enum class Gender {
    UNISEX,
    FEMALE,
    MEN
}

enum class ProcurementType {
    BOUGHT,
    RECEIVED
}