/*
 *                             Sun Public License
 *
 * The contents of this file are subject to the Sun Public License Version
 * 1.0 (the "License").  You may not use this file except in compliance with
 * the License.  A copy of the License is available at http://www.sun.com/
 *
 * The Original Code is the SLAMD Distributed Load Generation Engine.
 * The Initial Developer of the Original Code is Neil A. Wilson.
 * Portions created by Neil A. Wilson are Copyright (C) 2004-2019.
 * Some preexisting portions Copyright (C) 2002-2006 Sun Microsystems, Inc.
 * All Rights Reserved.
 *
 * Contributor(s):  Neil A. Wilson
 */
package com.slamd.db;



import java.util.Arrays;

import com.unboundid.asn1.ASN1Element;
import com.unboundid.asn1.ASN1OctetString;
import com.unboundid.asn1.ASN1Sequence;



/**
 * This class defines a generic permission that may be associated with some
 * functionality in the SLAMD administrative interface.
 *
 *
 * @author  Neil A. Wilson
 */
public final class SLAMDPermission
{
  /**
   * The name of the encoded element that holds the name of this permission.
   */
  private static final String ELEMENT_NAME = "name";



  /**
   * The name of the encoded element that holds the user names associated with
   * this permission.
   */
  private static final String ELEMENT_USERS = "users";



  /**
   * The name of the encoded element that holds the groups names associated with
   * this permission.
   */
  private static final String ELEMENT_GROUPS = "groups";



  // The name associated with this permission.
  private final String name;

  // The names of the groups that have been assigned this permission.
  private String[] groupNames;

  // The names of the users that have been assigned this permission.
  private String[] userNames;



  /**
   * Creates a new permission with the provided information.
   *
   * @param  name        The name of this permission.
   * @param  userNames   The names of the users that have been assigned this
   *                     permission.
   * @param  groupNames  The names of the groups that have been assigned this
   *                     permission.
   */
  public SLAMDPermission(final String name, final String[] userNames,
                         final String[] groupNames)
  {
    this.name       = name;

    if (userNames == null)
    {
      this.userNames = new String[0];
    }
    else
    {
      this.userNames = userNames;
    }

    if (groupNames == null)
    {
      this.groupNames = new String[0];
    }
    else
    {
      this.groupNames = groupNames;
    }
  }



  /**
   * Retrieves the name of this permission.
   *
   * @return  The name of this permission.
   */
  public String getName()
  {
    return name;
  }



  /**
   * Retrieves the names of the users that have been assigned this permission.
   *
   * @return  The names of the users that have been assigned this permission.
   */
  public String[] getUserNames()
  {
    return userNames;
  }



  /**
   * Specifies the names of the users that have been assigned this permission.
   *
   * @param  userNames  The names of the users that have been assigned this
   *                    permission.
   */
  public void setUserNames(final String[] userNames)
  {
    if (userNames == null)
    {
      this.userNames = new String[0];
    }
    else
    {
      Arrays.sort(userNames);
      this.userNames = userNames;
    }
  }



  /**
   * Adds the provided user name to the set of users that have been assigned
   * this permission.
   *
   * @param  userName  The user name to add to the set of users that have been
   *                   assigned this permission.
   */
  public void addUserName(final String userName)
  {
    for (final String n : userNames)
    {
      if (n.equalsIgnoreCase(userName))
      {
        return;
      }
    }

    final String[] newUserNames = new String[userNames.length+1];
    System.arraycopy(userNames, 0, newUserNames, 0, userNames.length);
    newUserNames[userNames.length] = userName;
    Arrays.sort(newUserNames);
    userNames = newUserNames;
  }



  /**
   * Removes the specified user from the set of users that have been assigned
   * this permission.
   *
   * @param  userName  The user name to remove from the set of users that have
   *                   been assigned this permission.
   */
  public void removeUserName(final String userName)
  {
    int pos = -1;
    for (int i=0; i < userNames.length; i++)
    {
      if (userNames[i].equals(userName))
      {
        pos = i;
        break;
      }
    }

    if (pos == -1)
    {
      return;
    }

    final String[] newUserNames = new String[userNames.length-1];
    System.arraycopy(userNames, 0, newUserNames, 0, pos);
    System.arraycopy(userNames, pos+1, newUserNames, pos,
         (newUserNames.length - pos));
    userNames = newUserNames;
  }



  /**
   * Retrieves the names of the groups that have been assigned this permission.
   *
   * @return  The names of the groups that have been assigned this permission.
   */
  public String[] getGroupNames()
  {
    return groupNames;
  }



  /**
   * Specifies the names of the groups that have been assigned this permission.
   *
   * @param  groupNames  The names of the groups that have been assigned this
   *                     permission.
   */
  public void setGroupNames(final String[] groupNames)
  {
    if (groupNames == null)
    {
      this.groupNames = new String[0];
    }
    else
    {
      Arrays.sort(groupNames);
      this.groupNames = groupNames;
    }
  }



  /**
   * Adds the provided group name to the set of groups that have been assigned
   * this permission.
   *
   * @param  groupName  The group name to add to the set of groups that have
   *                    been assigned this permission.
   */
  public void addGroupName(final String groupName)
  {
    for (final String n : groupNames)
    {
      if (n.equals(groupName))
      {
        return;
      }
    }

    String[] newGroupNames = new String[groupNames.length+1];
    System.arraycopy(groupNames, 0, newGroupNames, 0, groupNames.length);
    newGroupNames[groupNames.length] = groupName;
    Arrays.sort(newGroupNames);
    groupNames = newGroupNames;
  }



  /**
   * Removes the specified group from the set of groups that have been assigned
   * this permission.
   *
   * @param  groupName  The group name to remove from the set of groups that
   *                    have been assigned this permission.
   */
  public void removeGroupName(final String groupName)
  {
    int pos = -1;
    for (int i=0; i < groupNames.length; i++)
    {
      if (groupNames[i].equals(groupName))
      {
        pos = i;
        break;
      }
    }

    if (pos == -1)
    {
      return;
    }

    final String[] newGroupNames = new String[groupNames.length-1];
    System.arraycopy(groupNames, 0, newGroupNames, 0, pos);
    System.arraycopy(groupNames, pos+1, newGroupNames, pos,
         (newGroupNames.length - pos));
    groupNames = newGroupNames;
  }



  /**
   * Indicates whether this permission is granted for the provided user.
   *
   * @param  user  The user for which to make the determination.
   *
   * @return  {@code true} if this permission is granted for the provided
   *          user, or {@code false} if not.
   */
  public boolean appliesToUser(final SLAMDUser user)
  {
    final String userName = user.getUserName();
    for (final String n : userNames)
    {
      if (n.equals(userName))
      {
        return true;
      }
    }

    final String[] userGroups = user.getGroupNames();
    for (final String permissionGroupName : groupNames)
    {
      for (final String userGroupName : userGroups)
      {
        if (userGroupName.equals(permissionGroupName))
        {
          return true;
        }
      }
    }

    return false;
  }



  /**
   * Encodes the information associated with this permission to an ASN.1
   * sequence.
   *
   * @return  The ASN.1 sequence containing the encoded permission data.
   */
  public ASN1Sequence encodeAsSequence()
  {
    final ASN1Element[] userElements  = new ASN1Element[userNames.length];
    for (int i=0; i < userNames.length; i++)
    {
      userElements[i] = new ASN1OctetString(userNames[i]);
    }

    final ASN1Element[] groupElements = new ASN1Element[groupNames.length];
    for (int i=0; i < groupNames.length; i++)
    {
      groupElements[i] = new ASN1OctetString(groupNames[i]);
    }

    final ASN1Element[] elementsToEncode = new ASN1Element[]
    {
      new ASN1OctetString(ELEMENT_NAME),
      new ASN1OctetString(name),
      new ASN1OctetString(ELEMENT_USERS),
      new ASN1Sequence(userElements),
      new ASN1OctetString(ELEMENT_GROUPS),
      new ASN1Sequence(groupElements)
    };

    return new ASN1Sequence(elementsToEncode);
  }



  /**
   * Encodes the information associated with this permission to a byte array.
   *
   * @return  The byte array containing the encoded permission data.
   */
  public byte[] encode()
  {
    return encodeAsSequence().encode();
  }



  /**
   * Decodes the provided ASN.1 sequence as a SLAMD permission.
   *
   * @param  permissionSequence  The ASN.1 sequence to be decoded.
   *
   * @return  The decoded permission.
   *
   * @throws  DecodeException  If a problem occurs while attempting to decode
   *                           the permission.
   */
  public static SLAMDPermission decodeSequence(
                                     final ASN1Sequence permissionSequence)
         throws DecodeException
  {
    try
    {
      String   name   = null;
      String[] users  = new String[0];
      String[] groups = new String[0];

      final ASN1Element[] elements = permissionSequence.elements();
      for (int i=0; i < elements.length; i += 2)
      {
        final String elementName =
             elements[i].decodeAsOctetString().stringValue();
        if (elementName.equals(ELEMENT_NAME))
        {
          name = elements[i+1].decodeAsOctetString().stringValue();
        }
        else if (elementName.equals(ELEMENT_USERS))
        {
          final ASN1Element[] userElements =
               elements[i+1].decodeAsSequence().elements();
          users = new String[userElements.length];
          for (int j=0; j < users.length; j++)
          {
            users[j] = userElements[j].decodeAsOctetString().stringValue();
          }
        }
        else if (elementName.equals(ELEMENT_GROUPS))
        {
          final ASN1Element[] groupElements =
               elements[i+1].decodeAsSequence().elements();
          groups = new String[groupElements.length];
          for (int j=0; j < groups.length; j++)
          {
            groups[j] = groupElements[j].decodeAsOctetString().stringValue();
          }
        }
      }

      return new SLAMDPermission(name, users, groups);
    }
    catch (final Exception e)
    {
      throw new DecodeException("Unable to decode the permission:  " + e, e);
    }
  }



  /**
   * Decodes the provided byte array as a SLAMD permission.
   *
   * @param  encodedPermission  The byte array containing the data to decode.
   *
   * @return  The decoded permission.
   *
   * @throws  DecodeException  If a problem occurs while attempting to decode
   *                           the permission.
   */
  public static SLAMDPermission decode(final byte[] encodedPermission)
         throws DecodeException
  {
    try
    {
      return decodeSequence(ASN1Sequence.decodeAsSequence(encodedPermission));
    }
    catch (final DecodeException de)
    {
      throw de;
    }
    catch (final Exception e)
    {
      throw new DecodeException("Unable to decode the permission:  " + e, e);
    }
  }
}

