// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: chat.proto

package com.example.chat;

public final class Chat {
  private Chat() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_chat_ChatMsg_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_chat_ChatMsg_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_chat_ChatResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_chat_ChatResponse_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\nchat.proto\022\004chat\"M\n\007ChatMsg\022\016\n\006roomId\030" +
      "\001 \001(\t\022\016\n\006userId\030\002 \001(\t\022\017\n\007content\030\003 \001(\t\022\021" +
      "\n\ttimestamp\030\004 \001(\003\"0\n\014ChatResponse\022\017\n\007suc" +
      "cess\030\001 \001(\010\022\017\n\007message\030\002 \001(\t2u\n\013ChatServi" +
      "ce\0222\n\013SendMessage\022\r.chat.ChatMsg\032\022.chat." +
      "ChatResponse\"\000\0222\n\016StreamMessages\022\r.chat." +
      "ChatMsg\032\r.chat.ChatMsg\"\0000\001B\024\n\020com.exampl" +
      "e.chatP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        });
    internal_static_chat_ChatMsg_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_chat_ChatMsg_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_chat_ChatMsg_descriptor,
        new java.lang.String[] { "RoomId", "UserId", "Content", "Timestamp", });
    internal_static_chat_ChatResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_chat_ChatResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_chat_ChatResponse_descriptor,
        new java.lang.String[] { "Success", "Message", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
