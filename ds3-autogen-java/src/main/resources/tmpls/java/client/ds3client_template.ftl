<#include "../copyright.ftl"/>

package ${packageName};

import com.spectralogic.ds3client.annotations.Action;
import com.spectralogic.ds3client.annotations.Resource;
import com.spectralogic.ds3client.annotations.ResponsePayloadModel;
import com.spectralogic.ds3client.commands.*;
import com.spectralogic.ds3client.commands.decorators.PutFolderRequest;
import com.spectralogic.ds3client.commands.decorators.PutFolderResponse;
import com.spectralogic.ds3client.commands.spectrads3.*;
import com.spectralogic.ds3client.commands.spectrads3.notifications.*;
import com.spectralogic.ds3client.models.JobNode;
import com.spectralogic.ds3client.networking.ConnectionDetails;

import com.spectralogic.ds3client.commands.parsers.interfaces.GetObjectCustomParserParameters;
import com.spectralogic.ds3client.commands.parsers.utils.Function;

import java.io.Closeable;
import java.io.IOException;

public interface Ds3Client extends Closeable {

    ConnectionDetails getConnectionDetails();

    PutFolderResponse putFolder(final PutFolderRequest request)
            throws IOException;

    <#list commands as cmd>
    ${javaHelper.toAnnotation(cmd.getAnnotationInfo())}
    ${cmd.documentation}
    ${cmd.getResponseName()} ${cmd.getName()?uncap_first}(final ${cmd.getRequestName()} request)
            throws IOException;

    </#list>
    <#list customCommands as cmd>
    ${javaHelper.toAnnotation(cmd.getAnnotationInfo())}
    ${cmd.documentation}
    ${cmd.getResponseName()} ${cmd.getName()?uncap_first}(final ${cmd.getRequestName()} request)
            throws IOException;

    </#list>

    GetObjectResponse getObject(final GetObjectRequest request,
                                final Function<GetObjectCustomParserParameters, GetObjectResponse> responseParser) throws IOException;

    Ds3Client newForNode(final JobNode node);
}