package space.accident.main.extra.gson

import com.google.gson.*
import net.minecraftforge.fluids.FluidRegistry
import net.minecraftforge.fluids.FluidStack
import java.lang.reflect.Type

class JsonFluidStack : JsonSerializer<FluidStack>, JsonDeserializer<FluidStack> {

    override fun serialize(src: FluidStack, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        val obj = JsonObject()
        obj.addProperty("fluid", src.getFluid().name)
        obj.addProperty("amount", src.amount)
        return obj
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): FluidStack {
        val obj = json.asJsonObject
        val fluid = obj["fluid"].asString
        val amount = obj["amount"].asInt
        return FluidStack(FluidRegistry.getFluid(fluid), amount)
    }
}