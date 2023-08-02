/*
package org.demo.task.manager.common.config;

import static org.jsoup.parser.Parser.unescapeEntities;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;
import java.io.IOException;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.PropertySource;
import service.common.utility.YamlPropertySourceFactory;
import software.amazon.awssdk.utils.StringUtils;

@JsonComponent
@PropertySource(value = "classpath:service.common.utility.yml", factory = YamlPropertySourceFactory.class)
public class DefaultJsonDeserializer extends JsonDeserializer<String>
    implements ContextualDeserializer {

  private final PolicyFactor POLICY_FACTORY;

  public DefaultJsonDeserializer(
      @Value("${html.sanitizers.allowElements:${default.html.sanitizers.allowElements}}") String allowedElements,
      @Value("${html.sanitizers.allowAttributes:${default.html.sanitizers.allowAttributes}}") String allowedAttributes) {
    this.POLICY_FACTORY = new HtmlPolicyBuilde().allowElements(allowedElements.split(", "))
        .allowUrlProtocols("https")
        .allowAttributes(allowedAttributes.split(", ")).onElements("p")
        .toFactory();
  }

  @Override
  public String deserialize(JsonParser parser, DeserializationContext ctxt)
      throws IOException {
    String value = parser.getValueAsString();
    if (StringUtils.isEmpty(value)) {
      return value;
    } else {
      String originalWithUnescaped = unescapeUntilNoHtmlEntityFound(value);
      return unescapeEntities(POLICY_FACTORY.sanitize(originalWithUnescaped), true);
    }
  }

  @Override
  public JsonDeserializer<?> createContextual(DeserializationContext ctxt,
      BeanProperty property)
      throws JsonMappingException {
    return this;
  }

  private String unescapeUntilNoHtmlEntityFound(final String value) {
    String unescaped = unescapeEntities(value, true);
    if (!unescaped.equals(value)) {
      return unescapeUntilNoHtmlEntityFound(unescaped);
    } else {
      return unescaped;
    }
  }
}
*/
