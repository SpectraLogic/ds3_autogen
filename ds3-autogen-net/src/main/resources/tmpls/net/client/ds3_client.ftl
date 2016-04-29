<#include "../common/copyright.ftl" />

using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;

using Ds3.Calls;
using Ds3.Models;
using Ds3.ResponseParsers;
using Ds3.Runtime;

namespace Ds3
{
    internal class Ds3Client : IDs3Client
    {
        private INetwork _netLayer;

        internal Ds3Client(INetwork netLayer)
        {
            this._netLayer = netLayer;
        }

        <#include "payload_command.ftl" />
        <#include "void_command.ftl" />

        public IDs3ClientFactory BuildFactory(IEnumerable<Ds3Node> nodes)
        {
            return new Ds3ClientFactory(this, nodes);
        }

        private class Ds3ClientFactory : IDs3ClientFactory
        {
            private readonly IDs3Client _client;
            private readonly IDictionary<Guid, Ds3Node> _nodes;

            public Ds3ClientFactory(IDs3Client client, IEnumerable<Ds3Node> nodes)
            {
                this._client = client;
                this._nodes = nodes.ToDictionary(node => node.Id);
            }

            public IDs3Client GetClientForNodeId(Guid? nodeId)
            {
                return this._client;
            }
        }
    }
}