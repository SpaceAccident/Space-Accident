package space.accident.main.extensions

import space.accident.main.MODNAME
import space.accident.main.SpaceAccident

fun Any.info(msg: Any) {
    SpaceAccident.LOG.info("[$MODNAME] $msg")
}

fun Any.debug(msg: Any) {
    SpaceAccident.LOG.debug("[$MODNAME] $msg")
}

fun Any.warn(msg: Any) {
    SpaceAccident.LOG.warn("[$MODNAME] $msg")
}

fun Any.error(msg: Any) {
    SpaceAccident.LOG.error("[$MODNAME] $msg")
}