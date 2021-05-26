package ru.keplerbr.homepage.graphql.data.scalar;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@NoArgsConstructor
public class DateScalarConfiguration {

  @Bean
  public GraphQLScalarType dateScalar() {
    return GraphQLScalarType.newScalar()
        .name("Date")
        .description("Date as GraphQL scalar.")
        .coercing(
            new Coercing<Date, String>() {
              @Override
              public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                if (!(dataFetcherResult instanceof Date)) {
                  throw new CoercingSerializeException("Date expected.");
                }
                return String.valueOf(dataFetcherResult);
              }

              @Override
              public Date parseValue(Object input) throws CoercingParseValueException {
                try {
                  return DateFormat.getDateInstance().parse(String.valueOf(input));
                } catch (ParseException e) {
                  throw new CoercingParseValueException(e);
                }
              }

              @Override
              public Date parseLiteral(Object input) throws CoercingParseLiteralException {
                try {
                  if (!(input instanceof StringValue)) {
                    throw new CoercingParseLiteralException("String value expected.");
                  }
                  return DateFormat.getDateInstance().parse(((StringValue) input).getValue());
                } catch (ParseException e) {
                  throw new CoercingParseLiteralException("Failed to parse date literal", e);
                }
              }
            })
        .build();
  }

}