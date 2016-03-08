<#include "../copyright.ftl"/>

package ${packageName};

import java.io.IOException;
import java.security.SignatureException;
import com.spectralogic.ds3client.commands.*;
import com.spectralogic.ds3client.commands.spectrads3.*;
import com.spectralogic.ds3client.commands.spectrads3.notifications.*;
import com.spectralogic.ds3client.models.Ds3Node;
import com.spectralogic.ds3client.networking.ConnectionDetails;
import com.spectralogic.ds3client.networking.NetworkClient;

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
    public ${cmd.getResponseName()} ${cmd.getName()?uncap_first}(${cmd.getRequestName()} request) throws IOException, SignatureException {
        return new ${cmd.getResponseName()}(this.netClient.getResponse(request));
    }
    </#list>

    <#list customCommands as cmd>
    @Override
    public ${cmd.getResponseName()} ${cmd.getName()?uncap_first}(${cmd.getRequestName()} request) throws IOException, SignatureException {
        ${cmd.getCustomBody()}
    }
    </#list>

    @Override
    public Ds3Client newForNode(final Ds3Node node) {
        final ConnectionDetails newConnectionDetails = ConnectionDetailsImpl.newForNode(node, this.getConnectionDetails());
        final NetworkClient newNetClient = new NetworkClientImpl(newConnectionDetails);
        return new Ds3ClientImpl(newNetClient);
    }

    @Override
    public void close() throws IOException {
        this.netClient.close();
    }
}