package io.yard.connector.config

import scala.concurrent.Future

import play.api.Configuration
import play.api.libs.json.{Json, Reads, Writes}

import io.yard.common.models._

object ConfigConnector extends io.yard.connector.api.Connector {
  def orgs(implicit reader: Reads[Organization]) = Seq(Organization("movio"))

  def read[T <: ProviderConfiguration](provider: Provider, organization: Option[Organization])(implicit reader: Reads[T]): Future[Option[T]] = Future.successful(reader.reads(Json.parse("{}")).asOpt)

  def save[T <: ProviderConfiguration](provider: Provider, organization: Option[Organization])(implicit writer: Writes[T]): Future[Boolean] = Future.successful(true)

  def read[T <: ModuleConfiguration](module: Module, organization: Option[Organization])(implicit reader: Reads[T]): Future[Option[T]] = Future.successful(reader.reads(Json.parse("{}")).asOpt)

  def save[T <: ModuleConfiguration](module: Module, organization: Option[Organization])(implicit writer: Writes[T]): Future[Boolean] = Future.successful(true)
}
