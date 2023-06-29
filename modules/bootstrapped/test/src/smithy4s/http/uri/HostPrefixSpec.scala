/*
 *  Copyright 2021-2022 Disney Streaming
 *
 *  Licensed under the Tomorrow Open Source Technology License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     https://disneystreaming.github.io/TOST-1.0.txt
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package smithy4s.http.uri

import smithy4s.example.DummyServiceOperation.DummyHostPrefix
import smithy4s.example.HostLabelInput

class HostPrefixSpec() extends munit.FunSuite {

  test("Parse host prefix pattern into host prefix segments") {
    val result = hostPrefixSegments("{head}--foo{tail}")
    expect(
      result == Option(
        Vector(
          HostPrefixSegment.label("head"),
          HostPrefixSegment.static("--foo"),
          HostPrefixSegment.label("tail")
        )
      )
    )
  }

  // "foo.{label1}--abc{label2}.{label3}.secure
  test("Write a valid Host Prefix    for DummyHostPrefix") {
    val result = HostEndpoint(DummyHostPrefix).get
    HostLabelInput(
      "mabeline",
      "virgo",
      smithy4s.example.Thing1
    )

    val expected =
      "foo." :: "mabeline" :: "--abc" :: "virgo" :: "." :: "smithy4s.example.Thing1" :: ".secure" :: Nil
    expect.eql(result, expected)
  }

}
