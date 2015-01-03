/**
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package pt.lighthouselabs.obd.commands;

import java.io.IOException;
import java.util.ArrayList;

import jssc.SerialPort;
import jssc.SerialPortException;

/**
 * Container for multiple {@link ObdMultiCommand} instances.
 */
public class ObdMultiCommand {

  private ArrayList<ObdCommand> commands;

  /**
   * Default ctor.
   */
  public ObdMultiCommand() {
    this.commands = new ArrayList<ObdCommand>();
  }

  /**
   * Add ObdCommand to list of ObdCommands.
   * 
   * @param command
   */
  public void add(ObdCommand command) {
    this.commands.add(command);
  }

  /**
   * Removes ObdCommand from the list of ObdCommands.
   * 
   * @param command
   */
  public void remove(ObdCommand command) {
    this.commands.remove(command);
  }

//  /**
//   * Iterate all commands, send them and read response.
//   */
//  public void sendCommands(InputStream in, OutputStream out)
//      throws IOException, InterruptedException {
//    for (ObdCommand command : commands)
//      command.run(in, out);
//  }
  
  public void sendCommands(SerialPort serialPort)
	      throws IOException, InterruptedException, SerialPortException {
	    for (ObdCommand command : commands)
	      command.run(serialPort);
	  }

  /**
   * 
   * @return
   */
  public String getFormattedResult() {
    StringBuilder res = new StringBuilder();
    for (ObdCommand command : commands)
      res.append(command.getFormattedResult()).append(",");

    return res.toString();
  }

}