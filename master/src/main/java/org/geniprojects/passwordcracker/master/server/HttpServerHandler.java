package org.geniprojects.passwordcracker.master.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import org.geniprojects.passwordcracker.master.service.Md5DecryptingController;
import org.geniprojects.passwordcracker.master.service.ResourceRetriever;
import org.geniprojects.passwordcracker.master.workers.interaction.Request;
import org.geniprojects.passwordcracker.master.workers.management.ManagementUtil;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpServerHandler extends SimpleChannelInboundHandler<Object> {
    private HttpRequest request;
    /** Buffer that stores the response content */
    private final StringBuilder headerBuf = new StringBuilder();
    private final StringBuilder contentBuf = new StringBuilder();
    private HttpResponseStatus responseStatus = null;
    private String contentType = "text/plain";

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpRequest) {
            HttpRequest request = this.request = (HttpRequest) msg;

            if (HttpHeaders.is100ContinueExpected(request)) {
                send100Continue(ctx);
            }

            headerBuf.setLength(0);
            contentBuf.setLength(0);

            //Serve Requests
            if (request.method() == HttpMethod.GET) {
                URI uriFromRequest = URI.create(request.uri());
                if (uriFromRequest.getQuery() == null) {
                    contentType = "text/html";
                    if (uriFromRequest.getPath() == null || uriFromRequest.getPath().equals("") || uriFromRequest.getPath().equals(ServerUtil.DEFAULT_PAGE_URL)) {
                        String htmlText = ResourceRetriever.retrieveResourceText(ServerUtil.DEFAULT_PAGE_URL);
                        contentBuf.append(htmlText);
                        responseStatus = OK;
                    } else {
                        String htmlText = ResourceRetriever.retrieveResourceText(uriFromRequest.getPath());
                        if (htmlText != null) {
                            contentBuf.append(htmlText);
                            responseStatus = OK;
                        } else {
                            responseStatus = NOT_FOUND;
                        }
                    }
                } else {
                    contentType = "text/plain";
                    QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
                    Map<String, List<String>> params = queryStringDecoder.parameters();
                    if (!params.isEmpty()) {
                        String decryptedString = Md5DecryptingController.decrypt(params.get(ServerUtil.FIELD_NAME).get(0));
                        contentBuf.append(decryptedString);
                    }
                }
            } else {
                responseStatus = NOT_FOUND;
            }

            if (msg instanceof LastHttpContent) {

                LastHttpContent trailer = (LastHttpContent) msg;

                if (!writeResponse(trailer, ctx)) {
                    // If keep-alive is off, close the connection once the content is fully written.
                    ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
                }
            }
        }
    }

    private static void appendDecoderResult(StringBuilder buf, HttpObject o) {
        DecoderResult result = o.getDecoderResult();
        if (result.isSuccess()) {
            return;
        }
    }

    private boolean writeResponse(HttpObject currentObj, ChannelHandlerContext ctx) {
        // Decide whether to close the connection or not.
        boolean keepAlive = HttpHeaders.isKeepAlive(request);
        // Build the response object.
        FullHttpResponse response = new DefaultFullHttpResponse(
                HTTP_1_1, responseStatus, Unpooled.copiedBuffer(contentBuf.toString(), CharsetUtil.UTF_8));

        response.headers().set(CONTENT_TYPE, contentType + " ;charset=UTF-8");

        if (keepAlive) {
            // Add 'Content-Length' header only for a keep-alive connection.
            response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
            // Add keep alive header as per:
            // - http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
            response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }


        // Write the response.
        ctx.write(response);

        return keepAlive;
    }

    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, CONTINUE);
        ctx.write(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
