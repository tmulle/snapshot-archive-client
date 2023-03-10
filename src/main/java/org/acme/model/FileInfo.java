package org.acme.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.Map;

/**
 * Class that holds information returned about a file
 *
 * @author tmulle
 */
@NoArgsConstructor
@Getter
@ToString
@RegisterForReflection
public class FileInfo {

    String id;

    String filename;
    Long length;
    Date uploadDate;

    Map<String, Object> metaData;

}