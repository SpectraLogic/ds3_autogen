<#include "../copyright.ftl"/>

package ${packageName};

import java.io.IOException;
import com.spectralogic.ds3client.commands.*;
import com.spectralogic.ds3client.commands.parsers.*;
import com.spectralogic.ds3client.commands.spectrads3.*;
import com.spectralogic.ds3client.commands.spectrads3.notifications.*;
import com.spectralogic.ds3client.models.JobNode;
import com.spectralogic.ds3client.networking.ConnectionDetails;
import com.spectralogic.ds3client.networking.NetworkClient;
import com.spectralogic.ds3client.networking.NetworkClientImpl;

public class Ds3ClientImpl implements Ds3Client {
    private final NetworkClient netClient;

    public Ds3ClientImpl(final NetworkClient netClient) {
        this.netClient = netClient;
    }

    NetworkClient getNetClient() {
        return this.netClient;
    }

    @Override
    public ConnectionDetails getConnectionDetails() {
        return this.netClient.getConnectionDetails();
    }

    <#list commands as cmd>
    @Override
    public ${cmd.getResponseName()} ${cmd.getName()?uncap_first}(final ${cmd.getRequestName()} request) throws IOException {
        return handleExceptions(this.netClient.getResponse(request, new ${cmd.getResponseName()}Parser()));
    }
    </#list>

    <#list customCommands as cmd>
    @Override
    public ${cmd.getResponseName()} ${cmd.getName()?uncap_first}(final ${cmd.getRequestName()} request) throws IOException {
        ${cmd.getCustomBody()}
    }
    </#list>

    @Override
    public Ds3Client newForNode(final JobNode node) {
        final ConnectionDetails newConnectionDetails = ConnectionDetailsImpl.newForNode(node, this.getConnectionDetails());
        final NetworkClient newNetClient = new NetworkClientImpl(newConnectionDetails);
        return new Ds3ClientImpl(newNetClient);
    }

    @Override
    public void close() throws IOException {
        this.netClient.close();
    }

    private static <T extends Ds3Response> T handleExceptions(final FutureTask<T> response) throws IOException {
        try {
            return response.get();
        } catch (final ExecutionException | InterruptedException  e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw new RuntimeException(e.getCause());
        }
    }
}