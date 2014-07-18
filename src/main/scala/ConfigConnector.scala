package io.yard.connector.config

import scala.concurrent.Future

import play.api.Configuration
import play.api.libs.json.{Json, Reads, Writes}

import io.yard.models._

object ConfigConnector extends io.yard.connector.api.Connector {
  def read[T <: ProviderConfiguration](organization: Organization, provider: Provider)(implicit reader: Reads[T]): Future[Option[T]] = Future.successful(reader.reads(Json.parse("{}")).asOpt)

  def save[T <: ProviderConfiguration](organization: Organization, provider: Provider)(implicit writer: Writes[T]): Future[Boolean] = Future.successful(true)

  def read[T <: ModuleConfiguration](organization: Organization, module: Module)(implicit reader: Reads[T]): Future[Option[T]] = Future.successful(reader.reads(Json.parse("{}")).asOpt)

  def save[T <: ModuleConfiguration](organization: Organization, module: Module)(implicit writer: Writes[T]): Future[Boolean] = Future.successful(true)
}
