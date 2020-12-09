package com.example.fn;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class PetsAuthFunction
{
    private static final DateTimeFormatter ISO8601;

    public PetsAuthFunction.Result handleRequest(PetsAuthFunction.Input input)
    {
        System.out.println(input.token);

        PetsAuthFunction.Result result = new PetsAuthFunction.Result();

        if (input.token != null && !input.token.equalsIgnoreCase("invalid"))
        {
            result.active = true;
            result.principal = "http://petsdb/users/mikar";
            result.scope = input.token.split(",");
            result.context = new HashMap();
            result.context.put("email", "mika.rinne@oracle.com");
        } else {
            result.active = false;
            result.wwwAuthenticate = "Bearer realm=\"Pets DB\"";
        }
        result.expiresAt = ISO8601.format(Instant.now().plus(5L, ChronoUnit.MINUTES).atOffset(ZoneOffset.UTC));
        return result;
    }

    static {
        ISO8601 = DateTimeFormatter.ISO_DATE_TIME;
    }

    public static class Result {
        public boolean active;
        public String principal;
        public String[] scope;
        public String expiresAt;
        public String wwwAuthenticate;
        public String clientId;
        public Map<String, String> context;
    }

    public static class Input {
        public String type;
        public String token;
    }
}