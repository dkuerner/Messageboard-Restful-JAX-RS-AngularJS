/*
 *
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2010 Oracle and/or its affiliates. All rights reserved.

Oracle and Java are registered trademarks of Oracle and/or its affiliates.
Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://jersey.dev.java.net/CDDL+GPL.html
 * or jersey/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at jersey/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package enterprise.messageboard.resources;

import enterprise.messageboard.entities.Message;
import enterprise.messageboard.exceptions.NotFoundException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.json.simple.*;
import org.json.simple.parser.*;


@Stateless
public class MessageBoardResourceBean {

    @Context
    private UriInfo ui;
    @EJB
    MessageHolderSingletonBean singleton;

    @GET
    @Produces({"application/json"})
    public List<Message> getMessages() {
        return singleton.getMessages();
    }

    @POST
    public Response addMessage(String msg) throws URISyntaxException {
        InputStream stream = new ByteArrayInputStream(msg.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream), 8);
        URI msgURI;
        try{
            JSONObject json = (JSONObject)new JSONParser().parse(reader);
            Message m = singleton.addMessage(json.get("message").toString());
            msgURI = ui.getRequestUriBuilder().path(Integer.toString(m.getUniqueId())).build();
            return Response.created(msgURI).build();
        }catch(Exception e){
            
        }
        return Response.ok().build();
        
        
    }

    @Path("{msgNum}")
    @GET
    @Produces({"application/json"})
    public Message getMessage(@PathParam("msgNum") int msgNum) throws NotFoundException {
        Message m = singleton.getMessage(msgNum);

        if (m == null) {
            throw new NotFoundException();
        }

        return m;

    }

    @Path("{msgNum}")
    @DELETE
    public void deleteMessage(@PathParam("msgNum") int msgNum) throws NotFoundException {
        boolean deleted = singleton.deleteMessage(msgNum);

        if (!deleted) {
            throw new NotFoundException();
        }
    }
}
