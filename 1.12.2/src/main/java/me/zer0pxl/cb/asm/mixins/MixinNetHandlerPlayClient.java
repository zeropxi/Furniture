package me.zer0pxl.cb.asm.mixins;

import io.netty.buffer.Unpooled;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraft.network.play.server.SPacketCustomPayload;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetHandlerPlayClient.class)
public class MixinNetHandlerPlayClient {

	@Shadow
	@Final
	private NetworkManager netManager;

	@Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
	public void injectCustomPayload(SPacketCustomPayload packetIn, CallbackInfo callbackInfo) {
		if (!"MM|CustomBlocks".equals(packetIn.getChannelName()) && !"cb:init".equals(packetIn.getChannelName()))
			return;

		callbackInfo.cancel();
		PacketBuffer buffer = packetIn.getBufferData();
		if (!buffer.readString(Short.MAX_VALUE).equals("StartHandshake"))
			return;

		PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
		packetBuffer.writeString("{\"protocol\":4}");
		netManager.sendPacket(new CPacketCustomPayload("MM|CustomBlocks", packetBuffer));
	}

}
