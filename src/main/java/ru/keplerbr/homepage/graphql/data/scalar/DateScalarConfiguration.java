package ru.keplerbr.homepage.graphql.data.scalar;

import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
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
        .coercing(new DateScalarCoercing())
        .build();
  }

  private static class DateScalarCoercing implements Coercing<Date, Long> {

    @Override
    public Long serialize(Object dataFetcherResult) throws CoercingSerializeException {
      if (!(dataFetcherResult instanceof Date)) {
        throw new CoercingSerializeException("Date object expected.");
      }
      return ((Date) dataFetcherResult).toInstant().toEpochMilli();
    }

    @Override
    public Date parseValue(Object input) throws CoercingParseValueException {
      if (!(input instanceof Long)) {
        throw new CoercingParseValueException("Date timestamp parsing error");
      }
      return new Date((Long) input);
    }

    @Override
    public Date parseLiteral(Object input) throws CoercingParseLiteralException {
      if (!(input instanceof Long)) {
        throw new CoercingParseValueException("Date timestamp parsing error");
      }
      return new Date((Long) input);
    }
  }

}