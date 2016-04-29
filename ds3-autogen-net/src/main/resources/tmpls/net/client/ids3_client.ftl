<#include "../common/copyright.ftl" />

using System.Collections.Generic;

using Ds3.Calls;
using Ds3.Models;

namespace Ds3
{
    /// <summary>
    /// The main DS3 API interface. Use Ds3Builder to instantiate.
    /// </summary>
    public interface IDs3Client
    {
        <#list payloadCommands as cmd>
        ${cmd.getResponseType()} ${cmd.getCommandName()}(${cmd.getRequestName()} request);
        </#list>
        <#list voidCommands as cmd>
        ${cmd.getResponseType()} ${cmd.getCommandName()}(${cmd.getRequestName()} request);
        </#list>

        /// <summary>
        /// For multi-node support (planned), this provides a means of creating
        /// a client that connects to the specified node id.
        /// </summary>
        /// <param name="nodes"></param>
        /// <returns></returns>
        IDs3ClientFactory BuildFactory(IEnumerable<Ds3Node> nodes);
    }
}