/*
 * Copyright 2017 DSATool team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package chargen.pros_cons_skills;

import dsatool.resources.ResourceManager;
import javafx.beans.property.IntegerProperty;
import jsonant.value.JSONObject;

public class ProConSelector extends ProConSkillSelector {

	private final JSONObject group = ResourceManager.getResource("data/" + type);
	private final GroupSelector selector = new GroupSelector(generationState, type, this, group, 2);

	public ProConSelector(JSONObject generationState, IntegerProperty gp, String type, IntegerProperty conGP, IntegerProperty seGP) {
		super(generationState, gp, type, conGP, seGP);
		possiblePane.setContent(selector.getControl());
	}

	@Override
	protected void activateGroupSelectors(JSONObject hero, JSONObject target) {
		selector.activate(hero, target);
	}

	@Override
	protected void deactivateGroupSelectors(JSONObject hero, JSONObject target) {
		selector.deactivate(hero, target);
	}
}
