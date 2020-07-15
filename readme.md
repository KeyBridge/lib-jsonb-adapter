# lib-jsonb-adapter

**Jakarta JSON Binding (JSON-B) utilities**

JSON-B is a standard binding layer for converting Java objects to/from JSON messages. It defines a default mapping algorithm for converting existing Java classes to JSON, while enabling developers to customize the mapping process through the use of Java annotations.

This utility library provides a convenient wrapper of the Jsonb the JSON Binding framework operations.

   * **fromJson**: read JSON input, deserialize to Java objects content tree
   * **toJson**: serialize Java objects content tree to JSON input 

A custom JsonbUtility class is provided to simplify marshal and unmarshal of Java objects.

**Usage**

The utility should _just work_ for marshaling and unmarshaling of Java classes to and from JSON encoded text.

```java
// Marshal a Java class to a json string.
Entity entity = //get entity//
String json = new JsonbUtility().marshal(service);

// Unmarshal a json string to a Java class
Entity recoveredEntity = new JsonbUtility().unmarshal(json, Entity.class);
      
// Entities should be identical
Assert.assertEquals(entity, recoveredEntity);

// Print the Json text for fun.
System.out.println(json);
```

Custom adapters and serializers / deserializaters.
JTS geometry adapters are included by default (we use these extensively), and other custom adapters are provided in the _ext_ package. Add these, or add your own using the standard 'with' method syntax.

```java
JsonbUtility jsonbUtility = new JsonbUtility()
  .withAdapters(new JsonbBase64CompressedAdapter());

```


**Dependency requirements**   
The following dependencies are marked as _provided_ by this library POM file  and must be added to your project. Note that JSONB is included with most current J2EE application servers. (Confirmed in Glassfish and Payara 5.x)

```xml
<dependencies>
    <!-- JSON-P -->
    <dependency>
        <groupId>org.glassfish</groupId>
        <artifactId>jakarta.json</artifactId>
        <version>1.1.5</version>
        <scope>runtime</scope>
    </dependency>
    <!-- JSON-B API -->
    <dependency>
        <groupId>jakarta.json.bind</groupId>
        <artifactId>jakarta.json.bind-api</artifactId>
        <version>1.0.1</version>
    </dependency>
    <!-- Yasson (JSON-B reference implementation) -->
    <dependency>
        <groupId>org.eclipse</groupId>
        <artifactId>yasson</artifactId>
        <version>1.0.3</version>
        <scope>runtime</scope>
    </dependency>
</dependencies>
```


**Encoding**   
In deserialization operations (fromJson), encoding of JSON data is detected automatically. In serialization operations (toJson), UTF-8 encoding is used by default for writing JSON data. 

**I-JSON support**   
I-JSON (short for "Internet JSON") is a restricted profile of JSON designed to maximize
interoperability and increase confidence that software can process it successfully with
predictable results.

**Adapters**   
Adapter is a class implementing javax.json.bind.adapter.JsonbAdapter interface. It has a custom code to convert the “unmappable” type (Original) into another one that JSONB can handle (Adapted). This library implements custom adapters for:

  * JTS Geometry
  * JTS Envelope
  * GZip compressed byte array
  * Map of Doubles

The Gzip and Map adapters are located in the _ext_ package and not included by default in the JsonbUtility configuration.

**Compatibility with JAX-B**   
A custom _PropertyVisibilityStrategy_ implementation is included in the default JsonbUtility configuration to recognize and respect JAXB annotations. The _JsonbPropertyVisibilityStrategy_ tries to emulate the `XmlAccessType.FIELD` JAXB annotation strategy where all methods are ignored unless specifically annotated with _XmlElement_ or _XmlAttribute_. All class fields are recognized by default unless annotated with _XmlTransient_.


**References**

  *  [Jakarta JSON Binding](http://json-b.net/)
  *  [RFC8259](https://tools.ietf.org/html/rfc8259) The JavaScript Object Notation (JSON) Data Interchange Format
  *  [RFC7493](https://tools.ietf.org/html/rfc7493) The I-JSON Message Format
  *  [JSR 367](https://jcp.org/en/jsr/detail?id=367) JSR 367: Java<sup>tm</sup> API for JSON Binding (JSON-B)
  *  [Jakarta XML Binding](https://projects.eclipse.org/projects/ee4j.jaxb-impl) Java™ Architecture for XML Binding
 






