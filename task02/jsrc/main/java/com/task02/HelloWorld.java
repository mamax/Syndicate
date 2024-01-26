package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.syndicate.deployment.annotations.LambdaUrlConfig;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.model.lambda.url.AuthType;
import com.syndicate.deployment.model.lambda.url.InvokeMode;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(lambdaName = "hello_world",
	roleName = "hello_world-role",
	isPublishVersion = true
)

@LambdaUrlConfig(
		authType = AuthType.NONE,
		invokeMode = InvokeMode.BUFFERED
)
public class HelloWorld implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
	private static final int SC_OK = 200;
	private static final int SC_BAD_REQUEST = 400;
	Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("statusCode", 200);
		resultMap.put("body", "Hello from Lambda");
		gson.toJson(resultMap);
		context.getLogger().log(apiGatewayProxyRequestEvent.toString());
		try {
			return new APIGatewayProxyResponseEvent()
					.withStatusCode(SC_OK)
					.withBody(gson.toJson(resultMap));
		} catch (IllegalArgumentException exception) {
			return new APIGatewayProxyResponseEvent()
					.withStatusCode(SC_BAD_REQUEST)
					.withBody(gson.toJson("An error message appeared"));
		}
	}
}
